package com.manh.controller;

import com.manh.debug.RequestContext;
import com.manh.model.Customer;
import com.manh.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ServiceCCustomerController {

    private CustomerService customerService;

    public ServiceCCustomerController(CustomerService customerService){
        this.customerService = customerService;
    }
    @GetMapping("/service-c/data")
    public ResponseEntity<Map<String, Object>> getData(@RequestHeader("correlation_id") String correlationId) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        RequestContext.get().addDebugMessage(ServiceCCustomerController.class.getName() + " : Woke Up from sleep: " + correlationId);

        Map<String, Object> response = new HashMap<>();
        Customer customer = new Customer(1, "David", "Smith");
        response.put("customer", customer);
        Thread.sleep(2000);
        long latency = System.currentTimeMillis() - startTime;
        response.put("service_b_latency_in_ms", latency);

        RequestContext.get().addDebugMessage("Woke Up from sleep: " + correlationId);
        RequestContext.get().addDebugMessage("Order fetched: " + customer.toString());

        //return ResponseEntity.ok(response);
        // Include correlation_id in the response headers
        return ResponseEntity.ok()
                .header("correlation_id", correlationId)
                .body(response);
    }

    @GetMapping("/api/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/api/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/api/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.saveCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping("/api/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/api/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
