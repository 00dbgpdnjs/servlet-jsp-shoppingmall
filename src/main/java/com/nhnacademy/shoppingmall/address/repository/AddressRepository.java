package com.nhnacademy.shoppingmall.address.repository;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    // Optional x: List는 여러 개의 요소를 포함할 수 있으며, 비어 있는 리스트를 반환하는 것으로 "존재하지 않는" 경우를 처리
    List<String> findByUserId(String userId);
    int save(Address address);
    int delete(Address address);
    int update(Address address);
    int count(Address address);
}
