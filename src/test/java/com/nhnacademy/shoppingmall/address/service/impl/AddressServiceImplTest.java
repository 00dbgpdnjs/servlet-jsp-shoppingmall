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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class AddressServiceImplTest {

    AddressRepository addressRepository = Mockito.mock(AddressRepository.class);
    AddressService addressService = new AddressServiceImpl(addressRepository);

    User testUser = new User("nhnacademy-test-user","nhn아카데미","nhnacademy-test-password","19900505", User.Auth.ROLE_USER,100_0000, LocalDateTime.now(),null);
//    List<String> addresses = new ArrayList<>(Arrays.asList("조선대학교1234"));
    List<String> addresses = new ArrayList<>(List.of("조선대학교1234"));
    Address testAddress = new Address(testUser.getUserId(),"조선대학교1234");



    @Test
    void getAddress() {
        Mockito.when(addressRepository.findByUserId(anyString())).thenReturn(addresses);
        Assertions.assertEquals(addressService.getAddress(testUser.getUserId()), addresses);
        Mockito.verify(addressRepository, Mockito.times(1)).findByUserId(anyString());
    }

    @Test
    void saveAddress() {
        Mockito.when(addressRepository.count(any())).thenReturn(0);
        Mockito.when(addressRepository.save(any())).thenReturn(1);

        addressService.saveAddress(testAddress);

        Mockito.verify(addressRepository, Mockito.times(1)).count(any(Address.class));
        Mockito.verify(addressRepository, Mockito.times(1)).save(any(Address.class));
    }

    @Test
    void deleteAddress() {
        Mockito.when(addressRepository.count(any())).thenReturn(1);
        Mockito.when(addressRepository.delete(any())).thenReturn(1);
        addressService.deleteAddress(testAddress);

        Mockito.verify(addressRepository, Mockito.times(1)).count(any(Address.class));
        Mockito.verify(addressRepository, Mockito.times(1)).delete(any(Address.class));
    }

    @Test
    void updateAddress() {
        Mockito.when(addressRepository.findByUserId(anyString())).thenReturn(addresses);
        Mockito.when(addressRepository.update(anyString(), any(Address.class))).thenReturn(1);

        Address newAddr = new Address(testAddress.getUserId(), "조선대학교123456789");
        addressService.updateAddress(testAddress.getAddress(), newAddr);

        Mockito.verify(addressRepository, Mockito.times(1)).findByUserId(anyString());
        Mockito.verify(addressRepository, Mockito.times(1)).update(anyString(), any(Address.class));
    }
}