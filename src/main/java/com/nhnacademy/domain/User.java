package com.nhnacademy.domain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;

@Getter
public class User {
    private final String userId;
    private final String userPassword;

    public User(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public Boolean login(HttpServletRequest request, String pwd) {
        if (this.userPassword.equals(pwd)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("id", this.userId);
            return true;
        }
        return false;
    }
}
