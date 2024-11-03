package com.nhnacademy.shoppingmall.order.domain;

public class Order {
    private String orderId;
    private final String userId;
    private final int pId;
    private final int quantity;

    public Order(String userId, int pId, int quantity) {
        this.userId = userId;
        this.pId = pId;
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public int getpId() {
        return pId;
    }

    public int getQuantity() {
        return quantity;
    }
}
