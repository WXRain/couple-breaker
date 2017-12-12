package vip.onno.breaker.service;

import java.util.List;

import vip.onno.breaker.pojo.weibo.Comment;
import vip.onno.breaker.pojo.weibo.CommentList;

public interface IWeiboCommentService {

    List<Comment> findComment(Long weiboId, Object target);

    CommentList fetchCommentPage(String url);

}
