package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.product.domain.Product;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    public void updateItem(int productId, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getpId() == productId) {
                item.setQuantity(quantity);
                return;
            }
        }
        log.error("productId {} not found", productId);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void removeItem(int productId) {
        items.removeIf(item -> item.getProduct().getpId() == productId);
    }
}
