package com.manh.filter;


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CorrelationIdFilter implements org.springframework.cloud.gateway.filter.GlobalFilter, Ordered {

    private static final String CORRELATION_ID = "correlation_id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Retrieve correlation_id from the request header or generate a new one
        String correlationId = exchange.getRequest().getHeaders().getFirst(CORRELATION_ID);
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString(); // Generate a new ID if missing
        }

        // Add the correlation_id to the request headers
        exchange.getRequest().mutate().header(CORRELATION_ID, correlationId).build();

        // Pass the correlation_id in the response headers
        exchange.getResponse().getHeaders().add(CORRELATION_ID, correlationId);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // Ensures this filter is applied early
    }
}
