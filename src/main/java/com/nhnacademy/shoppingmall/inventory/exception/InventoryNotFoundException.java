package com.nhnacademy.shoppingmall.inventory.exception;

public class InventoryNotFoundException extends RuntimeException{
    public InventoryNotFoundException(int productId) {
        super(String.format("productId not found:"+productId));
    }
}
