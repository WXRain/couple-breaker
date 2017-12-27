package vip.onno.breaker.common.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class CommonConfig {
    public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 "
        + "(KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";
    public static final String WEIBO_COMMENT_URL_FORMAT = "https://m.weibo.cn/api/comments/show?id=%d&page=%d";
    @Bean
    public HttpClient httpClientConfig() {
        return HttpClients.createDefault();
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

}
