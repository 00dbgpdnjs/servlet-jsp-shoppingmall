package com.nhnacademy.repositry.impl;

import com.nhnacademy.domain.Customer;
import com.nhnacademy.repositry.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryImplTest {
    CustomerRepository customerRepository = new CustomerRepositoryImpl();

    @Test
    void findById() {
        Customer expect = new Customer("1", "1234", "tom");
        Customer actual = customerRepository.findById("1").get();

        Assertions.assertEquals(expect.getUserId(),actual.getUserId());
        Assertions.assertEquals(expect.getUserPassword(),actual.getUserPassword());
        Assertions.assertEquals(expect.getUserName(),actual.getUserName());

    }
}