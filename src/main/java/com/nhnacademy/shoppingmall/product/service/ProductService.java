package com.nhnacademy.shoppingmall.product.service;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.category.domain.CategoryProduct;
import com.nhnacademy.shoppingmall.product.domain.Product;

import java.util.List;

public interface ProductService {
    Product getProduct(int productId);
    List<Product> getProducts(int page);
    List<Product> getProductsByCategory(int page, String categoryName);
    int getCount();
    int getCountByCategory(String categoryName);
    void saveProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int productId);
}
