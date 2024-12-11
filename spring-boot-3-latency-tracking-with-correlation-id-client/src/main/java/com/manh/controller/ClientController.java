package com.manh.controller;


import com.manh.debug.RequestContext;
import com.manh.model.CorrealatedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    Logger logger  = LoggerFactory.getLogger("ClientController");
    @Autowired
    private WebClient webClient;

    @GetMapping("/invoke")
    public ResponseEntity<Map<String, Object>> invokeService(
            @RequestHeader(value = "correlation_id", required = false) String correlationId) {

        String correlationIdHeader = correlationId != null ? correlationId : "default-correlation-id";

        logger.info("correlationId : " + correlationId);
        Map<String, Object> response = webClient.get()
                .uri("/server/aggregate") // Calls the Client-Facing Server
                .header("correlation_id", correlationIdHeader)
                .retrieve()
                .bodyToMono(Map.class)
                .block(); // Blocking for simplicity in this example

        return ResponseEntity.ok(response);
    }

    @GetMapping("/invoke/custom")
    public ResponseEntity<CorrealatedResponse> invokeCustomService(
            @RequestHeader(value = "correlation_id", required = false) String correlationId) {
        long startTime = System.currentTimeMillis();
        RequestContext.get().addDebugMessage(ClientController.class.getName() + " : Received Request: " + correlationId);

        String correlationIdHeader = correlationId != null ? correlationId : "default-correlation-id";
        logger.info("correlationId : " + correlationId);

        CorrealatedResponse response = webClient.get()
                .uri("/server/aggregate/custom") // Calls the Client-Facing Server
                .header("correlation_id", correlationIdHeader)
                .retrieve()
                .bodyToMono(CorrealatedResponse.class)
                .block(); // Blocking for simplicity in this example
        long intermediateLatency = System.currentTimeMillis() - startTime;
        response.setIntermediateServiceLatency(intermediateLatency);
        RequestContext.get().addDebugMessage(ClientController.class.getName() + " : Processing Successful: " + correlationId);

        // Include correlation_id in the response headers
        return ResponseEntity.ok()
                .header("correlation_id", correlationId)
                .body(response);
        //return ResponseEntity.ok(response);
    }
}
