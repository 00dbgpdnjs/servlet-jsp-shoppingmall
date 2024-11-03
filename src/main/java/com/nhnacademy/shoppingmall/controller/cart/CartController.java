package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Objects;

@RequestMapping("/cart/cart.do")
public class CartController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        Cart cart = (Cart) session.getAttribute("cart");
        if(Objects.isNull(cart)) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        return "shop/cart/cart";
    }
}
