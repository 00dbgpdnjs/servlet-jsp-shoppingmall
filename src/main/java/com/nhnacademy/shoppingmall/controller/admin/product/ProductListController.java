package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@RequestMapping("/admin/productList.do")
public class ProductListController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String pageParam = req.getParameter("page");

        int page = pageParam == null ? 1 : Integer.parseInt(pageParam);

        int cnt = productService.getCount();
        List<Product> products = productService.getProducts(page);
        log.debug("{} 페이지의 카테고리화된 상품 개수: {}", page, products.size());
        int pageCnt = cnt / 3;
        if (cnt % 3 > 0){
            pageCnt++;
        }

        req.setAttribute("products", products);
        req.setAttribute("pageCnt", pageCnt);
        req.setAttribute("page", page);

        return "shop/admin/product_list";
    }
}