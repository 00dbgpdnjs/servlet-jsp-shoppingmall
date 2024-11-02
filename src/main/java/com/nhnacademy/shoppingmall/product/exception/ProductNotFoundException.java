package com.nhnacademy.shoppingmall.product.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(int productId) {
        super(String.format("productId not found:"+productId));
    }
}
