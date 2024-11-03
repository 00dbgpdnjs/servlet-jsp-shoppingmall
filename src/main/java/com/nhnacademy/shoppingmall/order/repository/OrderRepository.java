package com.nhnacademy.shoppingmall.order.repository;

import com.nhnacademy.shoppingmall.order.domain.Order;

import java.util.Optional;

public interface OrderRepository {
    int save(Order order);
}
