package com.nhnacademy.shoppingmall.controller.product;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@RequestMapping("/productDetail.do")
public class ProductDetailController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productId = req.getParameter("product_id");

        if(Objects.isNull(productId) || productId.trim().isEmpty()) {
            throw new RuntimeException("product_id parameter is empty");
        }

        Product product = productService.getProduct(Integer.parseInt(productId));
        req.setAttribute("product", product);

        if(Objects.nonNull(product))
            addRecentlyViewedProduct(req.getSession(), product.getpId());

        return "shop/product/product_detail";
    }

    public void addRecentlyViewedProduct(HttpSession session, int productId) {
        List<Integer> viewedProducts = (List<Integer>) session.getAttribute("recentlyViewed");

        if (viewedProducts == null) {
            viewedProducts = new ArrayList<>();
        }

        // 중복 방지
        if (!viewedProducts.contains(productId)) {
            viewedProducts.addFirst(productId);
        }

        // 최대 5개만 유지
        if (viewedProducts.size() > 5) {
            viewedProducts = viewedProducts.subList(0, 5);
        }

        session.setAttribute("recentlyViewed", viewedProducts);
    }
}
