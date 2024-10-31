package com.nhnacademy.shoppingmall.common.filter;


import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;

@WebFilter("*")
public class AdminIncludeFilter extends HttpFilter {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        // ?? AdminCheckFilter 있으니까 "/admin/*" 은 제외

        HttpSession session = req.getSession(false);

        if(Objects.isNull(session) || Objects.isNull(session.getAttribute("id"))){
            chain.doFilter(req, res);
            return;
        }

        DbConnectionThreadLocal.initialize();
        User id = userService.getUser((String) session.getAttribute("id"));
        DbConnectionThreadLocal.reset();

        if(id.getUserAuth() == User.Auth.ROLE_ADMIN) {
            req.setAttribute("role", "admin");
        }

        chain.doFilter(req, res);
    }
}