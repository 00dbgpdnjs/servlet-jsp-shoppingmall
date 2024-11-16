package com.nhnacademy.repositry.impl;

import com.nhnacademy.repositry.CustomerRepository;
import com.nhnacademy.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    private final Map<String, Customer> userMap = new HashMap<>();

    public CustomerRepositoryImpl() {
        userMap.put("1", new Customer("1", "1234", "tom"));
        userMap.put("2", new Customer("2", "1234", "jake"));
    }

    @Override
    public Optional<Customer> findById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }
}
