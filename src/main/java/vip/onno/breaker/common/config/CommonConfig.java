package vip.onno.breaker.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vip.onno.breaker.core.WeiboCommentFetcher;

@Configuration
public class CommonConfig {
    public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 "
        + "(KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36";
    public static final String WEIBO_COMMENT_URL_FORMAT = "https://m.weibo.cn/api/comments/show?id=%d&page=%d";

    @Bean
    public WeiboCommentFetcher weiboCommentUtil() {
        return new WeiboCommentFetcher();
    }

}
