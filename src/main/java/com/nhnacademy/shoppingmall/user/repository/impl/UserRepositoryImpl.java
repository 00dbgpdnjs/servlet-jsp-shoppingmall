package com.nhnacademy.shoppingmall.user.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        /*todo#3-1 회원의 아이디와 비밀번호를 이용해서 조회하는 코드 입니다.(로그인)
          해당 코드는 SQL Injection이 발생합니다. SQL Injection이 발생하지 않도록 수정하세요.
         */
        // ??- 한 트랜잭션 마치고 반납하나 -> FrontServlet service()
        Connection connection = DbConnectionThreadLocal.getConnection();
//        String sql =String.format("select user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at from users where user_id='%s' and user_password ='%s'",
//                userId,
//                userPassword
//        );
        String sql = "select user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at from users where user_id= ? and user_password = ?";

        log.debug("sql:{}",sql);

        ResultSet rs = null;
        try(
//             Statement psmt = connection.createStatement();
//             ResultSet rs =  psmt.executeQuery(sql);
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1,userId);
            statement.setString(2,userPassword);
            rs = statement.executeQuery();

            if(rs.next()){
                User user = new User(
                        rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_password"),
                        rs.getString("user_birth"),
                        User.Auth.valueOf(rs.getString("user_auth")),
                        rs.getInt("user_point"),
                        Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                        Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                );
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } // ?? rs fianlly 로 반납 안 하나

        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String userId) {
        //todo#3-2 회원조회
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "select user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at from users where user_id= ?";

        log.debug("sql:{}",sql);

        ResultSet rs = null;
        try( PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1,userId);
            rs = statement.executeQuery();

            if(rs.next()){
                User user = new User(
                        rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_password"),
                        rs.getString("user_birth"),
                        User.Auth.valueOf(rs.getString("user_auth")),
                        rs.getInt("user_point"),
                        Objects.nonNull(rs.getTimestamp("created_at")) ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                        Objects.nonNull(rs.getTimestamp("latest_login_at")) ? rs.getTimestamp("latest_login_at").toLocalDateTime() : null
                );
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public int save(User user) {
        //todo#3-3 회원등록, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "insert into users(user_id, user_name, user_password, user_birth, user_auth, user_point, created_at, latest_login_at) values(?,?,?,?,?,?,?,?)";
//        String sql = "insert into users(user_id, user_name, user_password, user_birth, user_auth, user_point, created_at) values(?,?,?,?,?,?,?)";
        log.debug("sql:{}",sql);

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1, user.getUserId());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getUserPassword());
            statement.setString(4, user.getUserBirth());
            statement.setString(5, user.getUserAuth().name());
            statement.setInt(6, user.getUserPoint());
            statement.setString(7, user.getCreatedAt().toString());
            /* ?? latest_login_at  null 이면 (update 등 다른 것도)
                vs statement.setString(, null)
                    setNull을 사용하는 것이 더 명확하고 안전합니다.
                    setNull은 변수의 타입을 명시적으로 지정할 수 있어, 데이터베이스에서의 혼란을 줄이는 데 도움이 됩니다.
             */
//            statement.setString(8, user.getLatestLoginAt().toString());
            if (user.getLatestLoginAt() != null) {
                statement.setString(8, user.getLatestLoginAt().toString());
            } else {
                statement.setNull(8, java.sql.Types.TIMESTAMP);
            }

            int result = statement.executeUpdate();
            log.debug("save:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#3-4 회원삭제, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();

        String sql = "delete from users where user_id=?";

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, userId);
            int result = statement.executeUpdate();
            log.debug("result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(User user) {
        //todo#3-5 회원수정, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        // ?? user_point, created_at, latest_login_at 는 지울까
            // updateLatestLoginAtByUserId 있어서 latest_login_at 안해도 될 듯
        String sql = "update users set user_name=?, user_password=?, user_birth=?, user_auth=?, user_point=?, created_at=? where user_id=?";
        log.debug("update:{}",sql);

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            int index=0;
            statement.setString(++index, user.getUserName());
            statement.setString(++index, user.getUserPassword());
            statement.setString(++index, user.getUserBirth());
            statement.setString(++index, user.getUserAuth().name());
            statement.setInt(++index, user.getUserPoint());
            statement.setString(++index, user.getCreatedAt().toString());
//            statement.setString(++index, user.getLatestLoginAt().toString());
            statement.setString(++index, user.getUserId());

            int result = statement.executeUpdate();
            log.debug("result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt) {
        //todo#3-6, 마지막 로그인 시간 업데이트, executeUpdate()을 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();
        String sql = "update users set latest_login_at=? where user_id=?";
        log.debug("update:{}",sql);

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            int index=0;
            statement.setString(++index, latestLoginAt.toString());
            statement.setString(++index, userId);

            int result = statement.executeUpdate();
            log.debug("result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByUserId(String userId) {
        //todo#3-7 userId와 일치하는 회원의 count를 반환합니다.
        Connection connection = DbConnectionThreadLocal.getConnection();

        int count=0;
        String sql = "select count(*) as cnt from users where user_id=? ";
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
//        finally {
//            try {
//                rs.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
        return count;
    }

}
