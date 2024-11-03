package com.nhnacademy.shoppingmall.thread.request.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

// nhnmart-step10 SelfCheckoutRequest 참고
@Slf4j
public class PointChannelRequest extends ChannelRequest {
    private final User user;
    private final UserService userService;

    public PointChannelRequest(User user, UserService userService) {
        if(Objects.isNull(user) || Objects.isNull(userService)) {
            throw new IllegalArgumentException("User and UserService must not be null");
        }

        this.user = user;
        this.userService = userService;
    }

    @Override
    public void execute() {
        DbConnectionThreadLocal.initialize();
        //todo#14-5 포인트 적립구현, connection은 point적립이 완료되면 반납합니다.
        // ?? addPointsToUser 가 더 좋으려나
        log.debug("pointChannel execute");
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            log.error("userId {} 포인트 적립 실패", user.getUserId());
        }
        DbConnectionThreadLocal.reset();
    }
}
