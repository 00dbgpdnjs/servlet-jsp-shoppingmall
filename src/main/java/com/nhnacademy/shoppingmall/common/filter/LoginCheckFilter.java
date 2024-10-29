package com.nhnacademy.shoppingmall.common.filter;

import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter("/mypage/*")
public class LoginCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo#10 /mypage/ 하위경로의 접근은 로그인한 사용자만 접근할 수 있습니다.
        // 로그인하지 않는 사용자가 접근하면 /login.do 페이지로 redirect 합니다.
        // ?? 나중에 (로그인 부분 아직 안나옴)
        /* https://github.com/nhnacademy-bootcamp/java-servlet-jsp/blob/main/day02/02.Servlet%20Filter/실습03-LoginCheckFilter/index.adoc
            String requestUri = ((HttpServletRequest) servletRequest).getRequestURI();
            // excludeUrls에 포함되지 않는다면 ..
            if(!excludeUrls.contains(requestUri)){
                HttpSession session = ((HttpServletRequest) servletRequest).getSession(false);
                if(Objects.isNull(session)){
                    ((HttpServletResponse) servletResponse).sendRedirect("/login.html");
                    return;
                }
            }

            filterChain.doFilter(servletRequest, servletResponse);
         */

//        if ()
//            res.sendRedirect("/login.do");
    }
}