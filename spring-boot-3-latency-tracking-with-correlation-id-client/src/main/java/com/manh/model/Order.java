package com.manh.model;

import java.time.LocalDate;

public class Order {

    private long orderId;

    private String orderNumber;

    private String productName;


    private LocalDate orderDate;

    private int orderQuantity;
    private Double orderAmount;

    private long customerId;

    public Order(long orderId, String orderNumber, LocalDate orderDate, Double orderAmount, long customerId, int orderQuantity, String productName) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderAmount = orderAmount;
        this.customerId = customerId;
        this.orderQuantity = orderQuantity;
        this.productName = productName;
    }

    public Order() {
    }

    public long getOrderId() {
        return orderId;
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

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderNumber='" + orderNumber + '\'' +
                ", productName='" + productName + '\'' +
                ", orderDate=" + orderDate +
                ", orderQuantity=" + orderQuantity +
                ", orderAmount=" + orderAmount +
                ", customerId=" + customerId +
                '}';
    }
}
