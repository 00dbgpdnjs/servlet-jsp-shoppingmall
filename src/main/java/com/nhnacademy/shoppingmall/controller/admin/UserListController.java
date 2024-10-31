package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@RequestMapping("/admin/userList.do")
public class UserListController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String roleParam = req.getParameter("role");
        String pageParam = req.getParameter("page");

        String role = roleParam == null ? User.Auth.ROLE_USER.name() : roleParam;
        int page = pageParam == null ? 1 : Integer.parseInt(pageParam);

        List<User> users = userService.getUserByRole(page, role);
        log.debug("{} 권한 - {} 명", role, users.size());

        List<String> userIds = new ArrayList<>();
        users.forEach(user -> userIds.add(user.getUserId()));
        log.debug("권한: {}", userIds);
        req.setAttribute("userIds", userIds);

        return "shop/admin/user_list";
    }
}