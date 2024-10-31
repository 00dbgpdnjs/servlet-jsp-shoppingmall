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
@RequestMapping(method = RequestMapping.Method.POST,value = "/mypage/updateUser.do")
public class UserUpdatePostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("user_name");
        String pwd = req.getParameter("user_password");
        String birth = req.getParameter("user_birth");

        if(!isValid(name) || !isValid(pwd) || !isValid(birth)) {
            log.debug("이름 {}, 패스워드 {} 또는 생년월일 {} 입력 wrong", name, pwd, birth);
            return "redirect:/mypage/mypage.do";
        }

        String id = (String) req.getSession().getAttribute("id");
        User user = userService.getUser(id);

        try {
            user.setUserName(name);
            user.setUserPassword(pwd);
            user.setUserBirth(birth);
            userService.updateUser(user);
        } catch (UserNotFoundException e) {
            log.error("존재하지 않는 아이디 {}", id);
        }
        return "redirect:/mypage/mypage.do";
    }

    public boolean isValid(String s) {
        return Objects.nonNull(s) && !s.isEmpty();
    }
}