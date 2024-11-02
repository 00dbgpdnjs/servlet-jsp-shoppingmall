package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.exception.AddressNotFoundException;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.exception.ProductNotFoundException;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.Objects;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.POST,value = "/admin/productDelete.do")
public class ProductDeleteContoller implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productId = req.getParameter("product_id");

        /* 메서드 시그니쳐에 throws
            - 안하는 경우
                정의: 이 메서드는 예외를 던지지 않는다는 것을 의미합니다. 즉, 이 메서드 내에서 발생하는 예외는 처리되며, 호출하는 쪽에서 예외를 처리할 필요가 없습니다.
                사용 예: 일반적으로 이와 같이 정의된 메서드는 예외가 발생해도 안정적으로 실행되도록 설계된 경우에 사용됩니다.
                에러가 발생해도 직접 예외를 처리하고 호출자에게 전달x
                    try {
                    } catch (SomeException e) {
                    }
            - 하는 경우
                이 메서드는 해당 에러가 발생할 수 있음을 명시합니다.
                특정 상황에서 예외가 발생할 수 있음을 호출자에게 알리는 방식입
                public void saveAddress() throws SomeException {
                    if (someConditionFails) {
                        throw new SomeException();
                    }
                }
         */

        if(!isValid(productId)) {
            log.warn("product_id 파라미터 필요");
            throw new RuntimeException("product_id 파라미터 필요");
        }

        productService.deleteProduct(Integer.parseInt(productId));
        return "redirect:/admin/productList.do";
    }
    public boolean isValid(String s) {
        return Objects.nonNull(s) && !s.trim().isEmpty();
    }
}