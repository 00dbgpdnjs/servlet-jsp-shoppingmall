package com.nhnacademy.shoppingmall.product.service;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.category.domain.CategoryProduct;
import com.nhnacademy.shoppingmall.product.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(int page);
    int getCount();
    void saveProduct(Product product);
    void deleteProduct(int productId);
}
