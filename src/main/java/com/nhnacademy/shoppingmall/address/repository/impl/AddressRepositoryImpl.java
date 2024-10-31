package com.nhnacademy.shoppingmall.address.repository.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class AddressRepositoryImpl implements AddressRepository {
    @Override
    public List<String> findByUserId(String userId) {
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select address from addresses where user_id= ?";

        log.debug("sql:{}",sql);

        ResultSet rs = null;
        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1,userId);
            rs = statement.executeQuery();

            List<String> addressList = new ArrayList<>();

            while(rs.next()){
                addressList.add(rs.getString("address"));
            }

            return addressList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int save(Address address) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "insert into addresses(user_id, address) values(?,?)";
        log.debug("sql:{}",sql);

        try( PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1, address.getUserId());
            statement.setString(2, address.getAddress());

            int result = statement.executeUpdate();
            log.debug("save:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(Address address) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "delete from addresses where user_id=? and address=?";

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, address.getUserId());
            statement.setString(1, address.getAddress());
            int result = statement.executeUpdate();
            log.debug("result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Address address) {
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "update addresses set address=? where user_id=?";
        log.debug("update:{}",sql);

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            int index=0;
            statement.setString(++index, address.getAddress());
            statement.setString(++index, address.getUserId());

            int result = statement.executeUpdate();
            log.debug("result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
