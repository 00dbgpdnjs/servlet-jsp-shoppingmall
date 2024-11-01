package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.category.domain.CategoryProduct;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.user.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public int save(Product product) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "insert into Product(p_name, p_price) values(?,?)";
        log.debug("sql:{}",sql);

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1, product.getpName());
            statement.setInt(2, product.getpPrice());

            int result = statement.executeUpdate();
            log.debug("save:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int count() {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) from Product";
        ResultSet rs = null;

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            rs = psmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(Objects.nonNull(rs)) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }

    @Override
    public Page<Product> findAll(int page, int pageSize) {
        int offset = (page-1) * pageSize;

        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select p_id, p_name, p_price, thumbnail_image, detail_image from Product limit  ?,?";
        ResultSet rs = null;

        log.debug("sql:{}",sql);
        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setInt(1,offset);
            psmt.setInt(2,pageSize);
            rs= psmt.executeQuery();
            List<Product> products = new ArrayList<>(pageSize);

            while(rs.next()){
                Product p = new Product(
                                rs.getInt("p_id"),
                                rs.getString("p_name"),
                                rs.getInt("p_price"),
                                rs.getString("thumbnail_image"),
                                rs.getString("detail_image")
                );

                p.setCategoryNames(getCategoryNamesByPId(p.getpId()));
                products.add(p);
            }

            long total =0;

            if(!products.isEmpty()){
                total = count();
            }

            return new Page<Product>(products, total);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(Objects.nonNull(rs)){
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<String> getCategoryNamesByPId(int pId) throws SQLException {
        Connection connection = DbConnectionThreadLocal.getConnection();

        List<String> categoryNames = new ArrayList<>();
        String sql = "SELECT category_name FROM Product_Category natural join Category WHERE p_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                categoryNames.add(resultSet.getString("category_name"));
            }
        }
        return categoryNames;
    }
}
