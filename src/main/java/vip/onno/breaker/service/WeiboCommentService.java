package vip.onno.breaker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
import vip.onno.breaker.core.WeiboCommentFetcher;

@Service
public class WeiboCommentService implements IWeiboCommentService {
    @Resource
    private WeiboCommentFetcher weiboCommentUtil;
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
        return weiboCommentUtil.fetchCommentListByUrl(url);
    }

    @Override
    public CommentList fetchCommentListByIdPage(Long weiboId, Integer page) {
        return weiboCommentUtil.fetchCommentListByUrl(String.format(CommonConfig.WEIBO_COMMENT_URL_FORMAT, weiboId, page));
    }

    @Override
    public List<Comment> fetchCommentsById(Long weiboId) {
        return weiboCommentUtil.fetchCommentsById(weiboId);
    }

    @Override
    public List<Comment> fetchCommentsByIdWithCondition(Long weiboId, Predicate<Comment> contition) {
        return weiboCommentUtil.fetchCommentsByIdWithCondition(weiboId, contition);
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
        Optional<CommentList> option = Optional.ofNullable(weiboCommentUtil.fetchCommentListByIdPage(weiboId, page));
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
