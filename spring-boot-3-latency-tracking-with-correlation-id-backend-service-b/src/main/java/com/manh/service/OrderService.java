package com.manh.service;

import com.manh.model.Customer;
import com.manh.model.Order;
import com.manh.model.OrderLineItem;
import com.manh.repository.CustomerRepository;
import com.manh.repository.OrderLineItemRepository;
import com.manh.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineItemRepository orderLineItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get order by ID
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
    }

    // Create a new order
    public Order createOrder(Order order) {
        Customer customer = customerRepository.findById(order.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + order.getCustomerId()));

        // Set the customer for the order
        order.setCustomer(customer);

        // Ensure bidirectional relationship
        order.getOrderLineItems().forEach(item -> item.setOrder(order));
        // Ensure bidirectional relationship
        order.getOrderLineItems().forEach(item -> item.setOrder(order));
        return orderRepository.save(order);
    }

    // Update an order
    public Order updateOrder(Long orderId, Order updatedOrder) {
        Order existingOrder = getOrderById(orderId);

        existingOrder.setOrderNumber(updatedOrder.getOrderNumber());
        existingOrder.setOrderDate(updatedOrder.getOrderDate());
        existingOrder.setOrderAmount(updatedOrder.getOrderAmount());
        existingOrder.setOrderQuantity(updatedOrder.getOrderQuantity());

        // Update line items
        existingOrder.getOrderLineItems().clear();
        updatedOrder.getOrderLineItems().forEach(item -> {
            item.setOrder(existingOrder);
            existingOrder.addOrderLineItem(item);
        });

        return orderRepository.save(existingOrder);
    }

    // Delete an order
    public void deleteOrder(Long orderId) {
        Order order = getOrderById(orderId);
        orderRepository.delete(order);
    }

    // Add a new line item to an existing order
    public Order addOrderLineItem(Long orderId, OrderLineItem orderLineItem) {
        Order order = getOrderById(orderId);
        order.addOrderLineItem(orderLineItem);
        return orderRepository.save(order);
    }

    // Remove a line item from an existing order
    public Order removeOrderLineItem(Long orderId, Long lineItemId) {
        Order order = getOrderById(orderId);

        OrderLineItem lineItem = orderLineItemRepository.findById(lineItemId)
                .orElseThrow(() -> new RuntimeException("Order line item not found with id " + lineItemId));

        order.removeOrderLineItem(lineItem);
        orderLineItemRepository.delete(lineItem);

        return orderRepository.save(order);
    }
}
