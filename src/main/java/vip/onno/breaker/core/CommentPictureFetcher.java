package vip.onno.breaker.core;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommentPictureFetcher {
    private WeiboCommentFetcher weiboCommentFetcher;
    private ExecutorService executorService;

    public CommentPictureFetcher() {
        weiboCommentFetcher = new WeiboCommentFetcher();
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void downloadCommentPictures(Long weiboId) {
        weiboCommentFetcher.fetchCommentsByIdWithCondition(weiboId, comment -> Objects.nonNull(comment.getPic()))
            .parallelStream()
            .forEach(comment -> executorService.submit(new CommentPicDownloader(comment)));
    }

}
