package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.category.service.impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.transaction.Transactional;
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
        return "shop/product/product_detail";
    }
}
