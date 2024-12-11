package com.manh.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Order {


    private long orderId;

    private Integer customerId;

    private String productName;

    private String orderNumber;

    private LocalDate orderDate;

    private Double orderAmount;

    private int orderQuantity;


    private Customer customer;


    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    public Order() {
    }

    public Order(String orderNumber, LocalDate orderDate, Double orderAmount, int orderQuantity) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderAmount = orderAmount;
        this.orderQuantity = orderQuantity;
    }

    public Order(int i, String s, LocalDate now, double v, int orderQuantity, int customerId, String productName) {
        this.customer = new Customer();
        this.orderId = i;
        this.orderNumber = s;
        this.orderDate = now;
        this.orderQuantity = orderQuantity;
        this.orderAmount = v;
        this.customer.setCustomerId(customerId);
        this.productName = productName;
    }

    public long getOrderId() {
        return orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public void addOrderLineItem(OrderLineItem lineItem) {
        this.orderLineItems.add(lineItem);
        lineItem.setOrder(this);
    }

    public void removeOrderLineItem(OrderLineItem lineItem) {
        this.orderLineItems.remove(lineItem);
        lineItem.setOrder(null);
    }
}
