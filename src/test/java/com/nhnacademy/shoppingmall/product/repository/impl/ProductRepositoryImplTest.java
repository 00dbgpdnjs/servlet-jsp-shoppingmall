package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.user.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryImplTest {
//    ProductRepository productRepository = new ProductRepositoryImpl();
//    Product product;
//
//    @BeforeEach
//    void setUp() throws SQLException {
//        DbConnectionThreadLocal.initialize();
//        product = new Product("nhnacademy-t-shirt", 10000);
//        productRepository.save(product);
//    }
//
//    @AfterEach
//    void tearDown() throws SQLException {
//        DbConnectionThreadLocal.setSqlError(true);
//        DbConnectionThreadLocal.reset();
//    }

//    @Test
//    void save() {
//        Product newProduct = new Product("nhnacademy-t-shirt2",10000);
//        int result = productRepository.save(newProduct);
//        Assertions.assertAll(
//                ()->Assertions.assertEquals(1,result),
//                ()->Assertions.assertEquals(newProduct, productRepository.findById(newUser.getUserId()).get())
//        );
//    }
}