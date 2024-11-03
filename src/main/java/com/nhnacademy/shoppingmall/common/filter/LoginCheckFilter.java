package com.nhnacademy.shoppingmall.common.filter;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@WebFilter({"/mypage/*", "/orderAction.do"})
public class LoginCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo#10 /mypage/ 하위경로의 접근은 로그인한 사용자만 접근할 수 있습니다.
        // 로그인하지 않는 사용자가 접근하면 /login.do 페이지로 redirect 합니다.
        /* https://github.com/nhnacademy-bootcamp/java-servlet-jsp/blob/main/day02/02.Servlet%20Filter/실습03-LoginCheckFilter/index.adoc

         */
        HttpSession session = req.getSession(false);
        if(Objects.isNull(session) || Objects.isNull(session.getAttribute("id"))){
            res.sendRedirect("/login.do");
            return;
        }

//        log.debug("session id : {}", session.getAttribute("id"));
        chain.doFilter(req, res);
    }
}