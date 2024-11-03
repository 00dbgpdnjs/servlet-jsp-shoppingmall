package com.nhnacademy.shoppingmall.common.initialize;

import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.worker.WorkerThread;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import java.util.Set;

// ??- point thread 를 왜 따로
    // 주문결제 시 포인트 적립은 독립된 Thread 내에서 동작합니다.
    //반대로 포인트 적립이 실패하더라도 주문결제는 정상 처리 됩니다.
// https://github.com/GJ-BE-8/servlet-jsp-shoppingmall/blob/main/docs/03.문제/14.포인트.adoc
public class PointThreadInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        RequestChannel requestChannel = new RequestChannel(10);
        //todo#14-1 servletContext에 requestChannel을 등록합니다.
        ctx.setAttribute("requestChannel", requestChannel);

        //todo#14-2 WorkerThread 사작합니다.
        new WorkerThread(requestChannel).start();
    }
}
