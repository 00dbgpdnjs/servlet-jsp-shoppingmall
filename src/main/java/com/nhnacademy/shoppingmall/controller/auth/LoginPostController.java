package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;

@Slf4j
/*?? @Transactional
    해당 메소드의 시작과 끝 사이에서 트랜잭션이 시작되고 끝납니다.
    예외가 발생하면 자동으로 트랜잭션을 롤백
    해당 클래스의 메소드들이 트랜잭션을 관리
    ?? 다른 필요한 컨트롤러도 어노테이션 붙이기
 */
@Transactional
@RequestMapping(method = RequestMapping.Method.POST,value = "/loginAction.do")
public class LoginPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //todo#13-2 로그인 구현, session은 60분동안 유지됩니다.

        String id = req.getParameter("user_id");
        String pwd = req.getParameter("user_password");

        if(!isValid(id) || !isValid(pwd)) {
            log.debug("아이디 {} 또는 패스워드 {} 가 ...", id, pwd);
            return "redirect:/login.do";
        }

        // ?? 서비스에서 해야하나. 그러려면 req 전달해야되는데
        try {
            User user = userService.doLogin(id, pwd);
            //session 있으면 가져오고 없으면 생성
                // JSESSIONID 쿠키는 자동으로 응답에 포함되어 클라이언트로 전송됩니다.
            HttpSession session = req.getSession();
            session.setAttribute("id",id);
            session.setMaxInactiveInterval(60 * 60); // 60분 (3600초)
//            resp.sendRedirect("/login");
            //?? 리디렉션 해야하지 않나
//            return "shop/main/index";
            return "redirect:/index.do";
        } catch (UserNotFoundException e) {
            log.debug("아이디 {} 또는 패스워드 {} 가 일치하지 않습니다.", id, pwd);
//            resp.sendRedirect("/login.html");
//            RequestDispatcher rd = req.getRequestDispatcher("/login.html");
//            rd.forward(req,resp);
            return "redirect:/login.do";
        }
    }

    public boolean isValid(String s) {
        return Objects.nonNull(s) && !s.isEmpty();
    }
}
