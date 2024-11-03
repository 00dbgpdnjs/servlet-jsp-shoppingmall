package com.nhnacademy.shoppingmall.inventory.domain;

public class Inventory {
    private final int pId;
    private final int inventoryQuantity;

    public Inventory(int pId, int inventoryQuantity) {
        this.pId = pId;
        this.inventoryQuantity = inventoryQuantity;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }
}
