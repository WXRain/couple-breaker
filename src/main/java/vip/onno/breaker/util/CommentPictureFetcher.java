package vip.onno.breaker.util;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import vip.onno.breaker.service.IWeiboCommentService;

public class CommentPictureFetcher {
    @Resource
    private IWeiboCommentService weiboCommentService;
    @Resource
    private ExecutorService executorService;

    public void downloadCommentPictures(Long weiboId) {
        weiboCommentService.fetchCommentsByIdWithCondition(weiboId, comment -> Objects.nonNull(comment.getPic()))
            .parallelStream()
            .forEach(comment -> executorService.submit(new CommentPicDownloader(comment)));
    }

}
