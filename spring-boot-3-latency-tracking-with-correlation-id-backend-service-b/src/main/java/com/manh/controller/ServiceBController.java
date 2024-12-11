package com.manh.controller;

import com.manh.debug.RequestContext;
import com.manh.model.Order;
import com.manh.model.OrderLineItem;
import com.manh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ServiceBController {

    @GetMapping("/service-b/data")
    public ResponseEntity<Map<String, Object>> getData(@RequestHeader("correlation_id") String correlationId) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        RequestContext.get().addDebugMessage("Received Correlation ID: " + correlationId);

        Order order = new Order(1,"ORD/10001", LocalDate.now(), 120.99, 1, 10, "Non Stick Frying Pan");
        Map<String, Object> response = new HashMap<>();
        response.put("order", order);
        Thread.sleep(3000);
        long latency = System.currentTimeMillis() - startTime;
        response.put("service_b_latency_in_ms", latency);
        RequestContext.get().addDebugMessage("Woke Up from sleep: " + correlationId);
        RequestContext.get().addDebugMessage("Order fetched: " + order.toString());

        // Include correlation_id in the response headers
        return ResponseEntity.ok()
                .header("correlation_id", correlationId)
                .body(response);

        //return ResponseEntity.ok(response);
    }

    @Autowired
    private OrderService orderService;

    // Get all orders
    @GetMapping("/api/orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Get an order by ID
    @GetMapping("/api/orders/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    // Create a new order
    @PostMapping("/api/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    // Update an order
    @PutMapping("/api/orders/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order updatedOrder) {
        Order order = orderService.updateOrder(orderId, updatedOrder);
        return ResponseEntity.ok(order);
    }

    // Delete an order
    @DeleteMapping("/api/orders/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    // Add a new line item to an existing order
    @PostMapping("/api/orders/{orderId}/line-items")
    public ResponseEntity<Order> addOrderLineItem(@PathVariable Long orderId, @RequestBody OrderLineItem orderLineItem) {
        Order updatedOrder = orderService.addOrderLineItem(orderId, orderLineItem);
        return ResponseEntity.ok(updatedOrder);
    }

    // Remove a line item from an existing order
    @DeleteMapping("/api/orders/{orderId}/line-items/{lineItemId}")
    public ResponseEntity<Order> removeOrderLineItem(@PathVariable Long orderId, @PathVariable Long lineItemId) {
        Order updatedOrder = orderService.removeOrderLineItem(orderId, lineItemId);
        return ResponseEntity.ok(updatedOrder);
    }

}