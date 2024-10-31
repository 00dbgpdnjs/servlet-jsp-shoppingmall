package com.nhnacademy.shoppingmall.category.repository.impl;

import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public List<String> findAll() {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select category_name from Category order by category_name";

        log.debug("sql:{}",sql);

        ResultSet rs = null;
        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            rs = statement.executeQuery();

            List<String> categoryNames = new ArrayList<>();

            while(rs.next()){
                categoryNames.add(rs.getString("category_name"));
            }

            return categoryNames;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
