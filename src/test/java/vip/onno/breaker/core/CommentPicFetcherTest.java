package vip.onno.breaker.core;

import org.junit.Test;

public class CommentPicFetcherTest {
    private CommentPictureFetcher downloader = new CommentPictureFetcher();

    @Test
    public void  downloadCommentPicturesTest() {
        downloader.downloadCommentPictures(4189583543852683L);
    }

    public static void main(String[] args) {
        new CommentPicFetcherTest().downloadCommentPicturesTest();
    }

}
