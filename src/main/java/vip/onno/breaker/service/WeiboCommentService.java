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

import vip.onno.breaker.pojo.weibo.Comment;
import vip.onno.breaker.pojo.weibo.CommentList;
import vip.onno.breaker.pojo.weibo.CommentResponse;

@Service
public class WeiboCommentService implements IWeiboCommentService {
    @Resource
    private HttpClient httpClient;

    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 "
        + "(KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";
    private static final String WEIBO_COMMENT_URL = "https://m.weibo.cn/api/comments/show?id=%d&page=%d";
    private static final Logger LOGGER = LoggerFactory.getLogger(WeiboCommentService.class);

    @Override
    public List<Comment> findComment(Long weiboId, Object target) {
        List<Comment> matchComments = new ArrayList<>();
        for (int page = 1;; page++) {
            LOGGER.info("fetching weibo: {}", page);
            if (!this.findByUserIdOrName(weiboId, target, page, matchComments))
                return matchComments;
        }
    }

    @Override
    public CommentList fetchCommentPage(String url) {
        CommentList commentPageData = null;
        HttpUriRequest request = new HttpGet(url);
        request.setHeader("user_agent", USER_AGENT);
        try {
            HttpResponse response = httpClient.execute(request);
            int responseStatus = response.getStatusLine().getStatusCode();
            if (responseStatus == 200) {
                String responseContent = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
                LOGGER.info(responseContent);
                CommentResponse commentResponse = JSON.parseObject(responseContent, CommentResponse.class);
                commentPageData = commentResponse.getData();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.debug("comment page: {}", commentPageData);
        return commentPageData;
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
    private boolean findByUserIdOrName(Long weiboId, Object target, int page, List<Comment> result) {
        Optional<CommentList> option = Optional.ofNullable(
            this.fetchCommentPage(String.format(WEIBO_COMMENT_URL, weiboId, page)));
        option.ifPresent(commentPageData -> {
            List<Comment> matched = Stream.of(commentPageData.getData(),
                commentPageData.getHotData()) // 同时取得普通评论和热门评论
                .filter(Objects::nonNull)
                // 将普通评论和热门评论的stream合并
                .flatMap(List<Comment>::parallelStream)
                .filter(comment -> {
                    if (target instanceof Long)
                        return Objects.equals(target, comment.getUser().getId());
                    else if (target instanceof String)
                        return Objects.equals(target, comment.getUser().getScreenName());
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
