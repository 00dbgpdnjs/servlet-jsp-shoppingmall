package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;
import com.nhnacademy.shoppingmall.pointHistory.repository.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.pointHistory.service.PointHistoryService;
import com.nhnacademy.shoppingmall.pointHistory.service.impl.PointHistoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Transactional
@RequestMapping("/mypage/pointHistory.do")
public class PointHistoryController implements BaseController {
    private final PointHistoryService pointHistoryService = new PointHistoryServiceImpl(new PointHistoryRepositoryImpl());
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String pageParam = req.getParameter("page");

        if(Objects.isNull(pageParam) || pageParam.trim().isEmpty()) {
            pageParam = "1";
        }
        int page = Integer.parseInt(pageParam);

        HttpSession session = req.getSession(false);
        String userId = (String) session.getAttribute("id");

        int cnt = pointHistoryService.getCountByUserId(userId);
        // ?? page가 pageCnt 초과하면..
        int pageCnt = cnt / 3;
        if (cnt % 3 > 0){
            pageCnt++;
        }

        List<PointHistory> pointHistory = pointHistoryService.getHistoryService(page, userId);
        req.setAttribute("pointHistory", pointHistory);
        req.setAttribute("pageCnt", pageCnt);
        req.setAttribute("page", page);

        return "shop/mypage/pointHistory";
    }
}