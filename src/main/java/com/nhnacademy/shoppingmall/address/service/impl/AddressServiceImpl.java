package com.nhnacademy.shoppingmall.address.service.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.exception.AddressAlreadyExistsException;
import com.nhnacademy.shoppingmall.address.exception.AddressNotFoundException;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.service.AddressService;

import java.util.List;

public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<String> getAddress(String userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    public void saveAddress(Address address) {
        if (addressRepository.count(address) > 0) {
            throw new AddressAlreadyExistsException(address.getAddress());
        }

        addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Address address) {
        if(addressRepository.count(address)<1) {
            throw new AddressNotFoundException(address.getAddress());
        }

        addressRepository.delete(address);
    }

    @Override
    public void updateAddress(String existingAddr, Address address) {
        if(addressRepository.findByUserId(address.getUserId()).isEmpty()) {
            throw new RuntimeException(String.format("유저 [%s]는 등록된 주소가 없습니다.", address.getUserId()));
        }

        int res = addressRepository.update(existingAddr, address);

        if(res < 1) {
            throw new RuntimeException("fail-updateAddress:" + address);
        } else if (res!=1) {
            throw new RuntimeException(res + " 개의 record가 수정됨: " + address);
        }
    }
}
