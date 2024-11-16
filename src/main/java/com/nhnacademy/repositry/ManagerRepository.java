package com.nhnacademy.repositry;

import com.nhnacademy.domain.Customer;
import com.nhnacademy.domain.Manager;

import java.util.Optional;

public interface ManagerRepository {
    Optional<Manager> findById(String id);
}
