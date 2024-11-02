package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/admin/productAdd.do")
public class ProductAddController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // ?? 카테고리: 체크박스 or toggle
        return "shop/admin/product_form";
    }
}
