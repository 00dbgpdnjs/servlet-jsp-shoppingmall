package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;

import java.util.Optional;

public interface ProductRepository {
    int save(Product product);
    int update(Product product);
    Optional<Product> findById(int productId);
    Optional<Product> findByName(String productName);
    Page<Product> findAll(int page, int pageSize);
    Page<Product> findByCategory(int page, int pageSize, String categoryName);
    int countByProductName(String productName);
    int countByProductId(int productId);
    int countByCategory(String categoryName);
    int count();
    int delete(int productId);
}
