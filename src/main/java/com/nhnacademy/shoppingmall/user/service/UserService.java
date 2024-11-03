package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.user.domain.User;

import java.util.List;

public interface UserService {

    User getUser(String userId);

    void saveUser(User user);

    void updateUser(User user);

    void updateUserPoint(String userId, int point);
    void deleteUser(String userId);

    User doLogin(String userId, String userPassword);

    List<User> getUserByRole(int page, String role);

    int getCountByRole(String role);

}
