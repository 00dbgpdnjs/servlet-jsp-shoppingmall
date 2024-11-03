package com.nhnacademy.shoppingmall.order.service;

import com.nhnacademy.shoppingmall.order.domain.Order;

import java.util.List;

public interface OrderService {
    void saveOrder(Order order);

    List<Order> getOrder(int page, String userId);

    int getCountByUserId(String userId);
}
