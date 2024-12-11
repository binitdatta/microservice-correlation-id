package com.manh.model;

public class CorrealatedResponse {

    Customer customer;

    Order order;

    long serviceALatency;

    long serviceBLatency;

    long intermediateServiceLatency;
    public CorrealatedResponse(Customer customer, Order order, long serviceALatency, long serviceBLatency, long intermediateServiceLatency) {
        this.customer = customer;
        this.order = order;
        this.serviceALatency = serviceALatency;
        this.serviceBLatency = serviceBLatency;
        this.intermediateServiceLatency = intermediateServiceLatency;
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

    public long getIntermediateServiceLatency() {
        return intermediateServiceLatency;
    }

    public void setIntermediateServiceLatency(long intermediateServiceLatency) {
        this.intermediateServiceLatency = intermediateServiceLatency;
    }
}
