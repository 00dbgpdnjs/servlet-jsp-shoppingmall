package com.nhnacademy.shoppingmall.product.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String productName){
        super(String.format("productName already exist:%s",productName));
    }
}
