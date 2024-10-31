package com.nhnacademy.shoppingmall.controller.mypage.user.address;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.exception.AddressAlreadyExistsException;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.Objects;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.POST,value = "/mypage/updateAddress.do")
public class AddressUpdatePostController implements BaseController {
    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String existingAddr = req.getParameter("existingAddr");
        String addr = req.getParameter("address");

        if (!isValid(addr)) {
            log.debug("파라미터 address [{}] wrong", addr);
            return "redirect:/mypage/addAddress.do";
        }

        try {
            Address address = new Address((String) req.getSession(false).getAttribute("id"), addr);
            addressService.updateAddress(existingAddr, address);
            return "redirect:/mypage/address.do";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "redirect:/mypage/address.do";
        }
    }

    public boolean isValid(String s) {
        return Objects.nonNull(s) && !s.isEmpty();
    }
}