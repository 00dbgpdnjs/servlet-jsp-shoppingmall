package com.nhnacademy.shoppingmall.inventory.service;

import com.nhnacademy.shoppingmall.inventory.domain.Inventory;

public interface InventoryService {
    Inventory getInventory(int productId);
}
