package com.nhnacademy.shoppingmall.order.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public int save(Order order) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "insert into Orders(user_id, p_id, quantity) values(?,?,?)";
        log.debug("sql:{}",sql);

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1, order.getUserId());
            statement.setInt(2, order.getpId());
            statement.setInt(3, order.getQuantity());

            int result = statement.executeUpdate();
            log.debug("save:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
