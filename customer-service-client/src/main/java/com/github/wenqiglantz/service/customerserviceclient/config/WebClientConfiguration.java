package com.github.wenqiglantz.service.customerserviceclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientConfiguration {

    @Bean
    public WebClient getWebClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8500")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
