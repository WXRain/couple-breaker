package vip.onno.breaker.service;

import java.util.List;
import java.util.function.Predicate;

import vip.onno.breaker.pojo.weibo.Comment;
import vip.onno.breaker.pojo.weibo.CommentList;

public interface IWeiboCommentService {

    /**
     * 找到与目标匹配的评论
     * @param weiboId 微博ID
     * @param target 查找不了
     * @return 匹配评论列表
     */
    List<Comment> findComment(Long weiboId, Object target);

    /**
     * 获取指定URL的评论列表
     * @param url 微博URL
     * @return 评论列表，没有结果时为null
     */
    CommentList fetchCommentListByUrl(String url);

    /**
     * 获取指定ID和页码的评论列表
     * @param weiboId 微博ID
     * @param page 页码
     * @return 评论列表，没有结果时为null
     */
    CommentList fetchCommentListByIdPage(Long weiboId, Integer page);

    /**
     * 获取指定ID的微博评论
     * @param weiboId 微博ID
     * @return 所有评论，没有结果时返回空集
     */
    List<Comment> fetchCommentsById(Long weiboId);

    /**
     * 获取指定ID和条件的微博评论
     * @param weiboId 微博ID
     * @param contition 条件
     * @return 所有符合条件的评论，没有结果时返回空集
     */
    List<Comment> fetchCommentsByIdWithCondition(Long weiboId, Predicate<Comment> contition);

}
