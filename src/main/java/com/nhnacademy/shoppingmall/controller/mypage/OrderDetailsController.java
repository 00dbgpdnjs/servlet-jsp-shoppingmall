package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Transactional
@RequestMapping("/mypage/orderDetails.do")
public class OrderDetailsController implements BaseController {
    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String pageParam = req.getParameter("page");

        if(Objects.isNull(pageParam) || pageParam.trim().isEmpty()) {
            pageParam = "1";
        }
        int page = Integer.parseInt(pageParam);

        HttpSession session = req.getSession(false);
        String userId = (String) session.getAttribute("id");

        int cnt = orderService.getCountByUserId(userId);
        int pageCnt = cnt / 3;
        if (cnt % 3 > 0){
            pageCnt++;
        }

        List<Order> orderList = orderService.getOrder(page, userId);
        req.setAttribute("orderList", orderList);
        req.setAttribute("pageCnt", pageCnt);
        req.setAttribute("page", page);

        return "shop/mypage/order_details";
    }
}
