package com.nhnacademy.repositry.impl;

import com.nhnacademy.domain.Customer;
import com.nhnacademy.domain.Manager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagerRepositoryImplTest {
    ManagerRepositoryImpl managerRepository = new ManagerRepositoryImpl();

    @Test
    void findById() {
        Manager expect = new Manager("sarah", "1234");
        Manager actual = managerRepository.findById("sarah").get();

        Assertions.assertEquals(expect.getUserId(),actual.getUserId());
        Assertions.assertEquals(expect.getUserPassword(),actual.getUserPassword());
    }
}