package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.category.domain.CategoryProduct;
import com.nhnacademy.shoppingmall.category.repository.CategoryProductRepository;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts(int page) {
        return productRepository.findAll(page, 3).getContent();
    }

    @Override
    public int getCount() {
        return productRepository.count();
    }

    @Override
    public void saveProduct(Product product) {
        if (productRepository.countByProductName(product.getpName()) > 0)
            throw new ProductAlreadyExistsException(product.getpName());

        int result = productRepository.save(product);

        if(result<1){
            throw new RuntimeException("fail-saveProduct:" + product.getpName());
        }
    }
}
