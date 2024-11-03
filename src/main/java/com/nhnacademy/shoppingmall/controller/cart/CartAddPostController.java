package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@RequestMapping(method = RequestMapping.Method.POST, value = "/cart/cartAdd.do")
public class CartAddPostController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productId = req.getParameter("product_id");

        if(Objects.isNull(productId) || productId.trim().isEmpty()) {
            throw new RuntimeException("product_id parameter is empty");
        }

        Product product = productService.getProduct(Integer.parseInt(productId));

        if(Objects.isNull(product)) {
            throw new RuntimeException(String.format("productId %s에 해당하는 상품이 존재하지 않아 장바구니에 담을 수 없음", productId));
        }


        HttpSession session = req.getSession();

        Cart cart = (Cart) session.getAttribute("cart");
        if(Objects.isNull(cart)) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        cart.addItem(product);

        return "shop/cart/cart";
    }
}