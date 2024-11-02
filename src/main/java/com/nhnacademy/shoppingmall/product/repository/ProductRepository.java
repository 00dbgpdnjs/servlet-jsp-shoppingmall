package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.category.domain.CategoryProduct;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.user.domain.User;

import java.util.Optional;

public interface ProductRepository {
    int save(Product product);
    Page<Product> findAll(int page, int pageSize);
    int countByProductName(String productName);
    int count();
    int countByProductId(int productId);
    int delete(int productId);
}
