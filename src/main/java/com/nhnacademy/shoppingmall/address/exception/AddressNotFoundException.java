package com.nhnacademy.shoppingmall.address.exception;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(String userId){
        super(String.format("user not found:"+userId));
    }
}
