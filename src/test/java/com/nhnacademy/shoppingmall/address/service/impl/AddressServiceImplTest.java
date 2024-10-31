package com.nhnacademy.shoppingmall.address.service.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class AddressServiceImplTest {

    AddressRepository addressRepository = Mockito.mock(AddressRepository.class);
    AddressService addressService = new AddressServiceImpl(addressRepository);

    User testUser = new User("nhnacademy-test-user","nhn아카데미","nhnacademy-test-password","19900505", User.Auth.ROLE_USER,100_0000, LocalDateTime.now(),null);
//    List<String> addresses = new ArrayList<>(Arrays.asList("조선대학교1234"));
    List<String> addresses = new ArrayList<>(List.of("조선대학교1234"));



    @Test
    void getAddress() {
        Mockito.when(addressRepository.findByUserId(anyString())).thenReturn(addresses);
        Assertions.assertEquals(addressService.getAddress(testUser.getUserId()), addresses);
        Mockito.verify(addressRepository, Mockito.times(1)).findByUserId(anyString());
    }
}