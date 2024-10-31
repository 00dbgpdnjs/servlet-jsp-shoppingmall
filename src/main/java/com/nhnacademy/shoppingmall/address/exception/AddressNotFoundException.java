package com.nhnacademy.shoppingmall.address.exception;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(String addr){
        super(String.format("address not found:"+addr));
    }
}
