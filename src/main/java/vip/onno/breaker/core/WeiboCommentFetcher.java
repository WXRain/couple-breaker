package vip.onno.breaker.core;

import com.alibaba.fastjson.JSON;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import vip.onno.breaker.common.config.CommonConfig;
import vip.onno.breaker.pojo.weibo.Comment;
import vip.onno.breaker.pojo.weibo.CommentList;
import vip.onno.breaker.pojo.weibo.CommentResponse;

public class WeiboCommentFetcher {
    private HttpClient httpClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(WeiboCommentFetcher.class);
    private static final int DEFAULT_PAGE_START_INDEX = 1;
    private static final int DEFAULT_COMMIT_NUM = 200;

    public WeiboCommentFetcher() {
         httpClient = HttpClients.createDefault();
    }

    public CommentList fetchCommentListByUrl(String url) {
        CommentList commentPageData = null;
        HttpUriRequest request = new HttpGet(url);
        request.setHeader("user_agent", CommonConfig.USER_AGENT);
        try {
            HttpResponse response = httpClient.execute(request);
            int responseStatus = response.getStatusLine().getStatusCode();
            if (responseStatus == HttpStatus.SC_OK) {
                String responseContent = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
                CommentResponse commentResponse = JSON.parseObject(responseContent, CommentResponse.class);
                commentPageData = commentResponse.getData();
                LOGGER.debug("Weibo comment page fetched.");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commentPageData;
    }

    public CommentList fetchCommentListByIdPage(Long weiboId, Integer page) {
        return fetchCommentListByUrl(String.format(CommonConfig.WEIBO_COMMENT_URL_FORMAT, weiboId, page));
    }

    public List<Comment> fetchCommentsById(Long weiboId) {
        List<Comment> commentList = new ArrayList<>(DEFAULT_COMMIT_NUM);
        for (int page = DEFAULT_PAGE_START_INDEX;;page++) {
            CommentList list = fetchCommentListByIdPage(weiboId, page);
            if (list == null)
                return commentList;
            commentList.addAll(list.getData());
        }
    }

    public List<Comment> fetchCommentsByIdWithCondition(Long weiboId, Predicate<Comment> contition) {
        List<Comment> comments = new ArrayList<>(DEFAULT_COMMIT_NUM);
        for (int page = DEFAULT_PAGE_START_INDEX;;page++) {
            Optional<CommentList> list = Optional.ofNullable(fetchCommentListByIdPage(weiboId, page));
            if (!list.isPresent())
                return comments;
            list.ifPresent(commentList -> commentList.getData().parallelStream()
                .filter(contition).forEach(comments::add));
        }
    }

}
