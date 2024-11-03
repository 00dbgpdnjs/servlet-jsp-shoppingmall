package com.nhnacademy.shoppingmall.order.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public int countByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "select count(*) from Orders where user_id = ?";
        ResultSet rs = null;

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1,userId);
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
    public Page<Order> findByUserId(int page, int pageSize, String userId) {
        int offset = (page-1) * pageSize;

        Connection connection = DbConnectionThreadLocal.getConnection();

        ResultSet rs = null;
        String sql="select order_id, p_id, quantity from Orders where user_id = ? order by order_date desc limit  ?,? ";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1,userId);
            psmt.setInt(2,offset);
            psmt.setInt(3,pageSize);
            rs= psmt.executeQuery();
            List<Order> orderList = new ArrayList<>(pageSize);

            while(rs.next()){
                orderList.add(
                        new Order(
                                rs.getInt("order_id"),
                                rs.getInt("p_id"),
                                rs.getInt("quantity")
                        )
                );
            }

            long total =0;

            if(!orderList.isEmpty()){
                total = countByUserId(userId);
            }

            return  new Page<>(orderList, total);

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
}
