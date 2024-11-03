package com.nhnacademy.shoppingmall.pointHistory.repository;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;
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

    @Override
    public int countByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        int count=0;
        String sql = "select count(*) from point_history where user_id=? ";
        ResultSet rs = null;

        try(PreparedStatement psmt = connection.prepareStatement(sql)){
            psmt.setString(1, userId);
            rs = psmt.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return count;
    }

    @Override
    public Page<PointHistory> findByUserId(int page, int pageSize, String userId) {
        int offset = (page-1) * pageSize;

        Connection connection = DbConnectionThreadLocal.getConnection();

        ResultSet rs = null;
        String sql="select points_used from point_history where user_id = ? order by created_at desc limit  ?,? ";

        try(PreparedStatement psmt = connection.prepareStatement(sql)) {
            psmt.setString(1,userId);
            psmt.setInt(2,offset);
            psmt.setInt(3,pageSize);
            rs= psmt.executeQuery();
            List<PointHistory> pointHistoryList = new ArrayList<>(pageSize);

            while(rs.next()){
                pointHistoryList.add(
                        new PointHistory(
                                rs.getInt("points_used")
                        )
                );
            }

            long total =0;

            if(!pointHistoryList.isEmpty()){
                total = countByUserId(userId);
            }

            return new Page<>(pointHistoryList, total);

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
