package vip.onno.breaker.common.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public HttpClient httpClientConfig() {
        return HttpClients.createDefault();
    }

}
