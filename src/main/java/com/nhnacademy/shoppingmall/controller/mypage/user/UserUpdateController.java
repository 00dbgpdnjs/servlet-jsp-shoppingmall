package com.nhnacademy.shoppingmall.controller.mypage.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.transaction.Transactional;

@Transactional
@RequestMapping("/mypage/updateUser.do")
public class UserUpdateController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String id = (String) req.getSession(false).getAttribute("id");
        User user = userService.getUser(id);
        req.setAttribute("user", user);
        return "shop/mypage/user_form";
    }
}
