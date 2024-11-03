package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.product.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getpId() == product.getpId()) {
                return;
            }
        }
        items.add(new CartItem(product, 1));
    }

    public void updateItem(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getpId() == product.getpId()) {
                item.setQuantity(quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void removeItem(int productId) {
        items.removeIf(item -> item.getProduct().getpId() == productId);
    }
}
