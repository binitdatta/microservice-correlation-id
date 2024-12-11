package com.manh.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.manh.debug.RequestContext;
import com.manh.model.CorrealatedResponse;
import com.manh.model.Customer;
import com.manh.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ServiceAController {

    Logger logger = LoggerFactory.getLogger("ServiceADirectController");

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/aggregate")
    public ResponseEntity<Map<String, Object>> aggregate(@RequestHeader("correlation_id") String correlationId) {
        long startTimeA = System.currentTimeMillis();

        Map<String, Object> finalResponse = new HashMap<>();

        Map<String, Object> customerResponseA = webClientBuilder.build()
                .get()
                .uri("http://localhost:8083/service-c/data")
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
                .uri("http://localhost:8083/service-b/data")
                .header("correlation_id", correlationId)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        finalResponse.put("Service_B_Response",orderResponseB.get("order"));

        long latencyB = System.currentTimeMillis() - startTimeB;
        finalResponse.put("service_b_latency",latencyB);

        return ResponseEntity.ok(finalResponse);
    }

    @GetMapping("/aggregate/custom")
    public ResponseEntity<CorrealatedResponse> aggregateCustomResponse(@RequestHeader("correlation_id") String correlationId) {
        long startTimeA = System.currentTimeMillis();
        RequestContext.get().addDebugMessage(ServiceAController.class.getName() + " : Received Request: " + correlationId);

        CorrealatedResponse correalatedResponse = new CorrealatedResponse();
        Map<String, Object> finalResponse = new HashMap<>();

        Map<String, Object> customerResponseA = webClientBuilder.build()
                .get()
                .uri("http://localhost:8083/service-c/data")
                .header("correlation_id", correlationId)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        long latencyA = System.currentTimeMillis() - startTimeA;
        finalResponse.put("Service_A_Response",customerResponseA.get("customer"));
        //customerResponseA.put("service_a_latency", latencyA);
        finalResponse.put("service_a_latency",latencyA);
        logger.info("Customer Service A : "+customerResponseA.get("customer"));

        // Extract the customer field and map it to the Customer class
        LinkedHashMap customerMap = (LinkedHashMap) customerResponseA.get("customer");
        Customer customer = objectMapper.convertValue(customerMap, Customer.class);
        RequestContext.get().addDebugMessage(ServiceAController.class.getName() + " : Extracted Customer Object From Map: " + customer.toString());

        correalatedResponse.setCustomer(customer);
        correalatedResponse.setServiceALatency(latencyA);

        long startTimeB = System.currentTimeMillis();

        Map<String, Object> orderResponseB = webClientBuilder.build()
                .get()
                .uri("http://localhost:8083/service-b/data")
                .header("correlation_id", correlationId)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        finalResponse.put("Service_B_Response",orderResponseB.get("order"));

        long latencyB = System.currentTimeMillis() - startTimeB;
        finalResponse.put("service_b_latency",latencyB);

        LinkedHashMap orderMap = (LinkedHashMap) orderResponseB.get("order");
        Order order = objectMapper.convertValue(orderMap, Order.class);

        RequestContext.get().addDebugMessage(ServiceAController.class.getName() + " : Extracted Order Object From Map: " + order.toString());

        correalatedResponse.setOrder(order);
        correalatedResponse.setServiceBLatency(latencyB);

        return ResponseEntity.ok()
                .header("correlation_id", correlationId) // Add the correlation_id header
                .body(correalatedResponse); // Add the response body
        // return ResponseEntity.ok(correalatedResponse);
    }
}

