package com.nhnacademy.shoppingmall.controller.mypage.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.POST,value = "/signupAction.do")
public class UserSignupPostController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("user_id");
        String name = req.getParameter("user_name");
        String pwd = req.getParameter("user_password");
        String birth = req.getParameter("user_birth");

        if(!isValid(id) || !isValid(name) || !isValid(id) || !isValid(birth)) {
            log.debug("아이디 {}, 이름 {}, 패스워드 {} 또는 생년월일 {} 입력 wrong", id, name, pwd, birth);
            return "redirect:/signup.do";
        }

        try {
            User user = new User(id, name, pwd, birth, User.Auth.ROLE_USER,100_0000, LocalDateTime.now(),null);
            userService.saveUser(user);
            return "redirect:/index.do";
        } catch (UserAlreadyExistsException e) {
            log.error("이미 존재하는 아이디 {}", id);
            // ?? 포워드 (LoginPostController)
            return "redirect:/signup.do";
        }
    }

    public boolean isValid(String s) {
        return Objects.nonNull(s) && !s.isEmpty();
    }
}