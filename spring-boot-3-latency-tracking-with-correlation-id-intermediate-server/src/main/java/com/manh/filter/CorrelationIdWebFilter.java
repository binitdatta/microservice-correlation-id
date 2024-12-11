package com.manh.filter;


import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class CorrelationIdWebFilter implements WebFilter {

    private static final String CORRELATION_ID = "correlation_id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Ensure the correlation_id is added to the response headers
        String correlationId = exchange.getRequest().getHeaders().getFirst(CORRELATION_ID);
        if (correlationId != null) {
            exchange.getResponse().getHeaders().add(CORRELATION_ID, correlationId);
        }
        return chain.filter(exchange);
    }
}

