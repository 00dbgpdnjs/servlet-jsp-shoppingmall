package com.nhnacademy.repositry;

import com.nhnacademy.domain.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(String id);
}
