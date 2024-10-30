package com.nhnacademy.shoppingmall.common.filter;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
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
@WebFilter("/admin/*")
public class AdminCheckFilter extends HttpFilter {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo#11 /admin/ 하위 요청은 관리자 권한의 사용자만 접근할 수 있습니다. ROLE_USER가 접근하면 403 Forbidden 에러처리

        // 로그인 체크
        // ?? 로그인 체크 부분 따로 빼야할 듯 너무 반복됨
        HttpSession session = req.getSession(false);
        /* Objects.isNull(session.getAttribute("id")) 도 해야 하는 이유
            JSESSIONID 쿠키는 특정 페이지에 처음 접근할 때도 세션이 생성될 수 있음 (/index.do)
            ?? /login.do 는 왜 안 생김
         */
        if(Objects.isNull(session) || Objects.isNull(session.getAttribute("id"))){
            res.sendRedirect("/login.do");
            return;
        }

        //
        DbConnectionThreadLocal.initialize();
        User id = userService.getUser((String) session.getAttribute("id"));
        DbConnectionThreadLocal.reset();

//        if (Objects.isNull(id)) {} // 로그인 체크 해서 null 일 수 없긴 함
        // 각 enum 값은 인스턴스가 하나뿐입니다. 따라서 == 연산자를 사용하여 비교할 수 있습니다.
        if(id.getUserAuth() != User.Auth.ROLE_ADMIN) {
            log.debug("관리자 권한이 없습니다.");
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have admin permission to access this resource.");
            return;
        }

        chain.doFilter(req, res);
    }
}
