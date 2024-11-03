package com.nhnacademy.shoppingmall.inventory.repository;

import com.nhnacademy.shoppingmall.inventory.domain.Inventory;

import java.util.Optional;

public interface InventoryRepository {
    Optional<Inventory> findByProductId(int productId);
}
