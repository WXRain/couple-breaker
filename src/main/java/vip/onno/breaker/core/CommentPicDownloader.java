package vip.onno.breaker.core;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import vip.onno.breaker.common.config.CommonConfig;
import vip.onno.breaker.pojo.weibo.Comment;

public class CommentPicDownloader implements Runnable {
    public static final File DOWNLOAD_DIR = new File(System.getProperty("user.home"), "weibo-pictures");
    private static final Logger LOGGER  = LoggerFactory.getLogger(CommentPicDownloader.class);
    private HttpClient httpClient;
    private Comment comment;

    public CommentPicDownloader(Comment comment) {
        httpClient = HttpClients.createDefault();
        this.comment = comment;
    }

    static {
        if (!DOWNLOAD_DIR.exists())
            DOWNLOAD_DIR.mkdirs();
    }

    @Override
    public void run() {
        Optional.ofNullable(comment.getPic().getUrl()).ifPresent(url -> {
            HttpUriRequest request = new HttpGet(url);
            request.setHeader("user_agent", CommonConfig.USER_AGENT);
            String fileName = String.format("%s-%d-%s.jpg", comment.getUser()
                .getScreenName(), comment.getUser().getId(), comment.getCreatedAt());

            try {
                HttpResponse response = httpClient.execute(request);
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    return;
                }
                try (OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(DOWNLOAD_DIR, fileName)));
                     InputStream in = new BufferedInputStream(response.getEntity().getContent())) {
                    byte[] buffer = new byte[8 * 1024];
                    while (in.read(buffer) != -1)
                        out.write(buffer);
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            LOGGER.info("Downloaded: {} -> {}", comment.getPic().getUrl(), fileName);
        });
    }
}
