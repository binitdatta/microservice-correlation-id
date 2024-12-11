package com.manh.service;

import com.manh.model.Customer;
import com.manh.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(int id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer saveCustomer(Customer customer) {
        customer.getAddresses().forEach(address -> address.setCustomer(customer));

        return customerRepository.save(customer);
    }

    public Customer updateCustomer(int id, Customer customerDetails) {
        Customer customer = getCustomerById(id);
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setDateOfJoining(customerDetails.getDateOfJoining());
        customer.setDateOfBirth(customerDetails.getDateOfBirth());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(int id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }
}
