package com.nhnacademy.shoppingmall.controller.index;

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
import java.util.List;
import java.util.Objects;

// ?? 왜 세 번이나 호출되지
@Transactional
@RequestMapping(method = RequestMapping.Method.GET,value = {"/index.do"})
public class IndexController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productName = req.getParameter("product_name");
        String pageParam = req.getParameter("page");
        String category = req.getParameter("category");

        if(Objects.nonNull(productName) && !productName.trim().isEmpty()) {
            Product product = productService.getProduct(productName);
            req.setAttribute("product", product);
        } else {
            int page = pageParam == null ? 1 : Integer.parseInt(pageParam);

            List<Product> products;
            int cnt = 0;
            if(Objects.isNull(category) || category.trim().isEmpty()) {
                products = productService.getProducts(page);
                cnt = productService.getCount();
            }else{
                products = productService.getProductsByCategory(page, category);
                cnt = productService.getCountByCategory(category);
            }

            int pageCnt = cnt / 3;
            if (cnt % 3 > 0){
                pageCnt++;
            }

            req.setAttribute("categories", categoryService.getCategories());
            req.setAttribute("products", products);
            req.setAttribute("pageCnt", pageCnt);
            req.setAttribute("page", page);
            req.setAttribute("recentlyViewed", (List<Integer>) req.getSession().getAttribute("recentlyViewed"));
        }

        return "shop/main/index";
    }
}