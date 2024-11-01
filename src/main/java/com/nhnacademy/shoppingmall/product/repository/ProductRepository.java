package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.category.domain.CategoryProduct;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.user.domain.User;

public interface ProductRepository {
    int save(Product product);
    int count();
    Page<Product> findAll(int page, int pageSize);

}
