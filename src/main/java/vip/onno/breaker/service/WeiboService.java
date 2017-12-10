package vip.onno.breaker.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import vip.onno.breaker.controller.ApiCommitController;
import vip.onno.breaker.pojo.Commit;
import vip.onno.breaker.pojo.CommitPage;

@Service
public class WeiboService {
    @Resource
    private HttpClient httpClient;

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 "
        + "(KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";
    private static final String WEIBO_COMMIT_URL = "https://m.weibo.cn/api/comments/show?id=%d&page=%d";
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiCommitController.class);

    public List<Commit> findCommit(Long weiboId, Object target) {
        List<Commit> matchCommits = new ArrayList<>();
        for (int page1 = 1, page2 = 2;; page1 += 2, page2 += 2) {
            LOGGER.info("fetching weibo: {}, {}", page1, page2);
            if (!(this.findByUserIdOrName(weiboId, target, page1, matchCommits)
                || this.findByUserIdOrName(weiboId, target, page2, matchCommits)))
                return matchCommits;
        }
    }

    private CommitPage fetchCommitPage(String url) {
        CommitPage commitPage = null;
        HttpUriRequest request = new HttpGet(url);
        request.setHeader("user_agent", USER_AGENT);
        try {
            HttpResponse response = httpClient.execute(request);
            int responseStatus = response.getStatusLine().getStatusCode();
            if (responseStatus == 200) {
                String responseContent = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
                LOGGER.info(responseContent);
                commitPage = JSON.parseObject(responseContent, CommitPage.class);
                if (commitPage.getData() == null && commitPage.getHotData() == null)
                    return null;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.debug("commit page: {}", commitPage);
        return commitPage;
    }

    /**
     * 查找符合条件的评论
     *
     * @param weiboId 微博ID
     * @param target 查找目标，可以是用户名或者ID
     * @param page 微博评论页
     * @param result 结果集
     * @return 当前页是否有数据
     */
    private boolean findByUserIdOrName(Long weiboId, Object target, int page, List<Commit> result) {
        Optional<CommitPage> option = Optional
            .ofNullable(this.fetchCommitPage(String.format(WEIBO_COMMIT_URL, weiboId, page)));
        option.ifPresent(commitPage -> {
            List<Commit> matched = Stream.of(commitPage.getData(), commitPage.getHotData()) // 同时取得普通评论和热门评论
                .filter(Objects::nonNull)
                // 将普通评论和热门评论的stream合并
                .flatMap(List<Commit>::stream)
                .filter(commit -> {
                    if (target instanceof Long)
                        return Objects.equals(target, commit.getUser().getId());
                    else if (target instanceof String)
                        return Objects.equals(target, commit.getUser().getScreenName());
                    else
                        return false;
                })
                .collect(Collectors.toList());
            LOGGER.debug("matched: {}", matched);
            result.addAll(matched);
        });
        return option.isPresent();
    }

}
