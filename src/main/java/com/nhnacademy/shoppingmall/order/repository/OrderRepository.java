package com.nhnacademy.shoppingmall.order.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;

public interface OrderRepository {
    int save(Order order);
    int countByUserId(String userId);
    Page<Order> findByUserId(int page, int pageSize, String userId);
}
