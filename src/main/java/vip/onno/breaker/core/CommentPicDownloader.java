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
                    int read = -1;
                    while ((read = in.read(buffer)) != -1) {
                        /*
                        BufferedOutputStream 配合 write(byte[] b) 方法 IO 效率最高
                        BufferedInputStream 并不能保证每次都能读到 buffer 大小的数据（尤其是网络通信受 MTU 限制），
                        但是 BufferOutputStream 的 write 方法仍然按照 buffer 的大小进行写入，
                        所以不能使用 BufferedOutputStream.write(byte[] buffer) 方法写入数据，
                        并且 write() 调用时长度要以每次读到的为准
                         */
                        out.write(buffer, 0, read);
                    }
                    out.flush();
                }
                LOGGER.info("Downloaded: {} -> {}", comment.getPic().getUrl(), fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
