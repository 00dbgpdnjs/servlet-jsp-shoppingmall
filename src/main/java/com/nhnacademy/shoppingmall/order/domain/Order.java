package com.nhnacademy.shoppingmall.order.domain;

public class Order {
    private int orderId;
    private String userId;
    private final int pId;
    private final int quantity;

    public Order(String userId, int pId, int quantity) {
        this.userId = userId;
        this.pId = pId;
        this.quantity = quantity;
    }

    public Order(int orderId, int pId, int quantity) {
        this.orderId = orderId;
        this.pId = pId;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
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
