package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.transaction.Transactional;
import java.util.Objects;

@Transactional
@RequestMapping(method = RequestMapping.Method.POST, value = "/cart/cartQuantityUpdate.do")
public class CartQuantityUpdateController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productId = req.getParameter("product_id");
        String quantity = req.getParameter("quantity");

        if (!isValid(productId) || !isValid(quantity)) {
            throw new RuntimeException("product_id, quantity parameter is empty");
        }

        if(Integer.parseInt(quantity) < 1) {
            throw new RuntimeException("quantity parameter less than 1");
        }

        HttpSession session = req.getSession(false);

        if(Objects.isNull(session)){
            throw new RuntimeException("no session");
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if(Objects.isNull(cart)) {
            throw new RuntimeException("no cart");
        }

        cart.updateItem(Integer.parseInt(productId), Integer.parseInt(quantity));

        return "redirect:/cart/cart.do";
    }

    public boolean isValid(String s) {
        return Objects.nonNull(s) && !s.trim().isEmpty();
    }
}