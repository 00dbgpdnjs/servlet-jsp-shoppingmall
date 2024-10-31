package com.nhnacademy.shoppingmall.address.service;

import com.nhnacademy.shoppingmall.address.domain.Address;

import java.util.List;

public interface AddressService {
    List<String> getAddress(String userId);
    void saveAddress(Address address);
}
