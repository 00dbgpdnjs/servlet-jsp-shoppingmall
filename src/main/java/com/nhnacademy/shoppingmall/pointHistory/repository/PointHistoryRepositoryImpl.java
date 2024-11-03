package com.nhnacademy.shoppingmall.pointHistory.repository;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class PointHistoryRepositoryImpl implements PointHistoryRepository {
    @Override
    public int save(PointHistory pointHistory) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "insert into point_history(user_id, points_used) values(?,?)";
        log.debug("sql:{}",sql);

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1, pointHistory.getUserId());
            statement.setInt(2, pointHistory.getPointsUsed());

            int result = statement.executeUpdate();
            log.debug("save:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
