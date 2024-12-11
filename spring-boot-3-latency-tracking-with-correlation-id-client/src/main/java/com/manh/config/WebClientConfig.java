package com.manh.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfig {

//    @Bean
//    public WebClient webClient(WebClient.Builder builder) {
//        return builder
//                .exchangeStrategies(ExchangeStrategies.builder()
//                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)) // Increase memory size if needed
//                        .build())
//                .baseUrl("http://localhost:8083") // Set default base URL for WebClient
//                .build();
//    }

    @Bean
    public WebClient webClientWithTimeout(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8083")
                .defaultHeader("User-Agent", "Spring WebClient")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .responseTimeout(Duration.ofSeconds(10)))) // Set a timeout
                .build();
    }
}

