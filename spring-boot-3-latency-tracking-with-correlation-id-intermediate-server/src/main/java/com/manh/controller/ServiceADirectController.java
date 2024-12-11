package com.manh.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ServiceADirectController {

    Logger logger = LoggerFactory.getLogger("ServiceADirectController");

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/aggregate/direct")
    public ResponseEntity<Map<String, Object>> aggregate(@RequestHeader("correlation_id") String correlationId) {
        long startTimeA = System.currentTimeMillis();

        Map<String, Object> finalResponse = new HashMap<>();

        Map<String, Object> customerResponseA = webClientBuilder.build()
                .get()
                .uri("http://localhost:5011/service-c/data")
                .header("correlation_id", correlationId)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        long latencyA = System.currentTimeMillis() - startTimeA;
        finalResponse.put("Service_A_Response",customerResponseA.get("customer"));
        customerResponseA.put("service_a_latency", latencyA);
        finalResponse.put("service_a_latency",latencyA);
        logger.info("Customer Service A : "+customerResponseA.get("customer"));

        long startTimeB = System.currentTimeMillis();

        Map<String, Object> orderResponseB = webClientBuilder.build()
                .get()
                .uri("http://localhost:5012/service-b/data")
                .header("correlation_id", correlationId)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        finalResponse.put("Service_B_Response",orderResponseB.get("order"));

        long latencyB = System.currentTimeMillis() - startTimeB;
        finalResponse.put("service_b_latency",latencyB);

        return ResponseEntity.ok(finalResponse);
    }
}

