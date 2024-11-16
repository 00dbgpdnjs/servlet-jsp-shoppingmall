package com.nhnacademy.domain;

import lombok.Getter;

@Getter
public class Customer extends User {
    private String userName;

    public Customer(String userId, String userPassword, String userName) {
        super(userId, userPassword);
        this.userName = userName;
    }
}
