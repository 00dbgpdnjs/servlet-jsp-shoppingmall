package com.nhnacademy.shoppingmall.inventory.service.impl;

import com.nhnacademy.shoppingmall.inventory.domain.Inventory;
import com.nhnacademy.shoppingmall.inventory.repository.InventoryRepository;
import com.nhnacademy.shoppingmall.inventory.service.InventoryService;

import java.util.Optional;

public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory getInventory(int productId) {
        Optional<Inventory> inventory = inventoryRepository.findByProductId(productId);
        return inventory.orElse(null);
    }
}
