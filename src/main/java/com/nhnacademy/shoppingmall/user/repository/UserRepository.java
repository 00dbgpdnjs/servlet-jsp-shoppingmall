package com.nhnacademy.shoppingmall.user.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.user.domain.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUserIdAndUserPassword(String userId, String userPassword);
    Optional<User> findById(String userId);
    int save(User user);
    int deleteByUserId(String userId);
    int update(User user);
    int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt);
    int updateUserPointByUserId(String userId, int point);
    int countByUserId(String userId);
    int countByRole(String role);
    // ??- jdbc_day2, (service - getBoards)
    Page<User> findAllByRole(int page, int pageSize, String role);

    boolean isFirstLoginOfTheDay(String userId);

}
