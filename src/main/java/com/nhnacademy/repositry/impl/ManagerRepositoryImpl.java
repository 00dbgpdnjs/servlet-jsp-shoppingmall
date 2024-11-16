package com.nhnacademy.repositry.impl;

import com.nhnacademy.domain.Customer;
import com.nhnacademy.domain.Manager;
import com.nhnacademy.repositry.CustomerRepository;
import com.nhnacademy.repositry.ManagerRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ManagerRepositoryImpl implements ManagerRepository {
    private final Map<String, Manager> userMap = new HashMap<>();

    public ManagerRepositoryImpl() {
        userMap.put("sarah", new Manager("sarah", "1234"));
        userMap.put("happy", new Manager("happy", "1234"));
    }

    @Override
    public Optional<Manager> findById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }
}
