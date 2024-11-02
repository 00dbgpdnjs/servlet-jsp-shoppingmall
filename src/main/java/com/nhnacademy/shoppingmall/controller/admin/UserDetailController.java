package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.Objects;

@Slf4j
@Transactional
@RequestMapping("/admin/userDetail.do")
public class UserDetailController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");

        if(Objects.isNull(id) || id.trim().isEmpty()) {
            return "redirect:/admin/userList.do";
        }

        User user = userService.getUser(id);

        if(Objects.isNull(user)) {
            log.error("존재하지 않는 회원: {}", id);
            return "redirect:/admin/userList.do";
        }

        req.setAttribute("user", user);
        return "shop/admin/user_detail";
    }
}
