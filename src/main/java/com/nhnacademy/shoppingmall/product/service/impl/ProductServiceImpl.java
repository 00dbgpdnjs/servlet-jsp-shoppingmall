package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.address.exception.AddressNotFoundException;
import com.nhnacademy.shoppingmall.category.domain.CategoryProduct;
import com.nhnacademy.shoppingmall.category.repository.CategoryProductRepository;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.exception.ProductNotFoundException;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProduct(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        return productOptional.orElse(null);
    }

    @Override
    public Product getProduct(String productName) {
        Optional<Product> productOptional = productRepository.findByName(productName);
        return productOptional.orElse(null);
    }

    @Override
    public List<Product> getProducts(int page) {
        return productRepository.findAll(page, 3).getContent();
    }

    @Override
    public List<Product> getProductsByCategory(int page, String categoryName) {
        return productRepository.findByCategory(page, 3, categoryName).getContent();
    }

    @Override
    public int getCount() {
        return productRepository.count();
    }

    @Override
    public int getCountByCategory(String categoryName) {
        return productRepository.countByCategory(categoryName);
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

    @Override
    public void updateProduct(Product product) {
        if (productRepository.countByProductId(product.getpId()) < 1)
            throw new ProductNotFoundException(product.getpId());

        int result = productRepository.update(product);
    }

    @Override
    public void deleteProduct(int productId) {
        if(productRepository.countByProductId(productId)<1) {
            throw new ProductNotFoundException(productId);
        }

        productRepository.delete(productId);
    }
}
