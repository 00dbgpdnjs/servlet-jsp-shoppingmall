package com.nhnacademy.shoppingmall.controller.mypage.user.address;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.exception.AddressAlreadyExistsException;
import com.nhnacademy.shoppingmall.address.exception.AddressNotFoundException;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.POST,value = "/mypage/deleteAddress.do")
public class AddressDelController implements BaseController {

    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String addr = req.getParameter("address");

        if(!isValid(addr)) {
            log.warn("address 파라미터 필요");
            throw new RuntimeException("address 파라미터 필요");
        }

        try {
            Address address = new Address((String) req.getSession(false).getAttribute("id"), addr);
            addressService.deleteAddress(address);
        } catch (AddressNotFoundException e) {
            log.error(e.getMessage());
        }
        return "redirect:/mypage/address.do";
    }

    public boolean isValid(String s) {
        return Objects.nonNull(s) && !s.trim().isEmpty();
    }
}
