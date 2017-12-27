package vip.onno.breaker.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommentPictureFetcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentPicDownloader.class);
    private WeiboCommentFetcher weiboCommentFetcher;
    private ExecutorService executorService;

    public CommentPictureFetcher() {
        weiboCommentFetcher = new WeiboCommentFetcher();
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void downloadCommentPictures(Long weiboId) {
        LOGGER.debug("Target weibo: {}", weiboId);
        weiboCommentFetcher.fetchCommentsByIdWithCondition(weiboId,
            comment -> Objects.nonNull(comment.getPic()) && comment.getPic().getUrl().endsWith(".jpg"))
            .parallelStream()
            .forEach(comment -> executorService.submit(new CommentPicDownloader(comment)));
        executorService.shutdown();
    }

}
