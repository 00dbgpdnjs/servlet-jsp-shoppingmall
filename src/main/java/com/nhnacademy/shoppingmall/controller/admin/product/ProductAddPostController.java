package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.exception.AddressAlreadyExistsException;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/productAdd.do")
public class ProductAddPostController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String pName = req.getParameter("p_name");
        String price = req.getParameter("p_price");

        if (!isValid(pName) || !isValid(price)) {
            log.debug("파라미터 입력 wrong");
            return "redirect:/admin/productAdd.do";
        }

        String thumbnailImage = req.getParameter("thumbnail_image");
        String detailImage = req.getParameter("detail_image");

        if (!isValid(thumbnailImage)) {thumbnailImage = null;}
        if (!isValid(detailImage)) {detailImage = null;}

        List<String> categories = new ArrayList<>();
        for(int i=1; i<=3; i++) {
            String category = req.getParameter("category_name"+i);
            if(Objects.nonNull(category) && !category.trim().isEmpty()) {
                categories.add(category);
            }
        }

        try {
            Product p = new Product(pName, Integer.parseInt(price), thumbnailImage, detailImage, categories);
            productService.saveProduct(p);
            return "redirect:/admin/productList.do";
        } catch (ProductAlreadyExistsException e) {
            log.error(e.getMessage());
            return "redirect:/admin/productAdd.do";
        }
    }

    public boolean isValid(String s) {
        return Objects.nonNull(s) && !s.trim().isEmpty();
    }
}