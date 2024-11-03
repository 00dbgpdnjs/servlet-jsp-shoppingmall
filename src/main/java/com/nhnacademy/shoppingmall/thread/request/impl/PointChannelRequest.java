package com.nhnacademy.shoppingmall.thread.request.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;
import com.nhnacademy.shoppingmall.pointHistory.service.PointHistoryService;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.Objects;

// nhnmart-step10 SelfCheckoutRequest 참고
@Slf4j
public class PointChannelRequest extends ChannelRequest {
    private final String userId;
    private final int point;
    private final UserService userService;
    private final PointHistoryService pointHistoryService;

    public PointChannelRequest(String userId, int point, UserService userService, PointHistoryService pointHistoryService) {
        if(Objects.isNull(userId) || Objects.isNull(userService) || Objects.isNull(pointHistoryService)) {
            throw new IllegalArgumentException("User, UserService and PointHistoryService must not be null");
        }

        if(point < 0) {
            throw new IllegalArgumentException("Point must be greater than 0");
        }

        this.userId = userId;
        this.point = point;
        this.userService = userService;
        this.pointHistoryService = pointHistoryService;
    }

    @Override
    public void execute() {
        DbConnectionThreadLocal.initialize();
        //todo#14-5 포인트 적립구현, connection은 point적립이 완료되면 반납합니다.
        // ?? addPointsToUser 가 더 좋으려나
        log.debug("pointChannel execute");
        try {
            userService.updateUserPoint(userId, point);
            pointHistoryService.savePointHistory(new PointHistory(userId, point));

        } catch (Exception e) {
            log.error("userId {} 포인트 적립 실패", userId);
        }
        DbConnectionThreadLocal.reset();
    }
}
