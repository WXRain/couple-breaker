package vip.onno.breaker.service;

import com.alibaba.fastjson.JSON;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import vip.onno.breaker.common.config.CommonConfig;
import vip.onno.breaker.pojo.weibo.Comment;
import vip.onno.breaker.pojo.weibo.CommentList;
import vip.onno.breaker.pojo.weibo.CommentResponse;

@Service
public class WeiboCommentService implements IWeiboCommentService {
    @Resource
    private HttpClient httpClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(WeiboCommentService.class);
    private static final int DEFAULT_PAGE_START_INDEX = 1;
    private static final int DEFAULT_COMMIT_NUM = 200;

    @Override
    public List<Comment> findComment(Long weiboId, Object target) {
        List<Comment> matchComments = new ArrayList<>();
        for (int page = DEFAULT_PAGE_START_INDEX;; page++) {
            LOGGER.info("fetching weibo: {}", page);
            if (!this.findByUserIdOrName(weiboId, target, page, matchComments))
                return matchComments;
        }
    }

    @Override
    public CommentList fetchCommentListByUrl(String url) {
        CommentList commentPageData = null;
        HttpUriRequest request = new HttpGet(url);
        request.setHeader("user_agent", CommonConfig.USER_AGENT);
        try {
            HttpResponse response = httpClient.execute(request);
            int responseStatus = response.getStatusLine().getStatusCode();
            if (responseStatus == HttpStatus.SC_OK) {
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

    @Override
    public CommentList fetchCommentListByIdPage(Long weiboId, Integer page) {
        return this.fetchCommentListByUrl(String.format(CommonConfig.WEIBO_COMMENT_URL_FORMAT, weiboId, page));
    }

    @Override
    public List<Comment> fetchCommentsById(Long weiboId) {
        List<Comment> commentList = new ArrayList<>(DEFAULT_COMMIT_NUM);
        for (int page = DEFAULT_PAGE_START_INDEX;;page++) {
            CommentList list = this.fetchCommentListByIdPage(weiboId, page);
            if (list == null)
                return commentList;
            commentList.addAll(list.getData());
        }
    }

    @Override
    public List<Comment> fetchCommentsByIdWithCondition(Long weiboId, Predicate<Comment> contition) {
        List<Comment> comments = new ArrayList<>(DEFAULT_COMMIT_NUM);
        for (int page = DEFAULT_PAGE_START_INDEX;;page++) {
            Optional<CommentList> list = Optional.ofNullable(this.fetchCommentListByIdPage(weiboId, page));
            if (!list.isPresent())
                return comments;
            list.ifPresent(commentList -> commentList.getData().parallelStream()
                .filter(contition).forEach(comments::add));
        }
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
        Optional<CommentList> option = Optional.ofNullable(this.fetchCommentListByIdPage(weiboId, page));
        option.ifPresent(commentPageData -> {
            // 同时取得普通评论和热门评论
            List<Comment> matched = Stream.of(commentPageData.getData(),commentPageData.getHotData())
                .filter(Objects::nonNull).flatMap(List<Comment>::parallelStream) // 将普通评论和热门评论的stream合并
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
