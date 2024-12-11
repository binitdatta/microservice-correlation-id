package com.manh.model;

public class CorrealatedResponse {

    Customer customer;

    Order order;

    long serviceALatency;

    long serviceBLatency;

    public CorrealatedResponse(Customer customer, Order order, long serviceALatency, long serviceBLatency) {
        this.customer = customer;
        this.order = order;
        this.serviceALatency = serviceALatency;
        this.serviceBLatency = serviceBLatency;
    }

    public CorrealatedResponse() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public long getServiceALatency() {
        return serviceALatency;
    }

    public void setServiceALatency(long serviceALatency) {
        this.serviceALatency = serviceALatency;
    }

    public long getServiceBLatency() {
        return serviceBLatency;
    }

    public void setServiceBLatency(long serviceBLatency) {
        this.serviceBLatency = serviceBLatency;
    }
}
