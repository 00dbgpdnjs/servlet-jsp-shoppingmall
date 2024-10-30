package com.nhnacademy.shoppingmall.thread.channel;


import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;

import java.util.LinkedList;
import java.util.Queue;

public class RequestChannel {
    private final Queue<ChannelRequest> queue;
    private final int queueMaxSize;

    public RequestChannel(int queueMaxSize) {
        this.queueMaxSize = queueMaxSize;
        this.queue = new LinkedList<>();
    }

    public synchronized ChannelRequest getRequest() throws InterruptedException {

        //todo#14-3 queue가 비어있다면 대기합니다.
        while(queue.isEmpty()) wait();

        //todo#14-4 queue에서 request 반환합니다.

        return queue.poll();
    }

    public synchronized void addRequest(ChannelRequest request) throws InterruptedException {

        //todo#14-5 queue가 가득차있다면 요청이 소비될 때까지 대기합니다.
        while(queue.size() >= queueMaxSize) wait();

        //todo#14-6 queue에 요청을 추가하고 대기하고 있는 스레드를 깨웁니다
        queue.add(request);
        notifyAll();

        /* notifyAll vs notify
        특정 조건에서 하나의 스레드만 필요할 때:
            notify
            예를 들어, 한 개의 작업만 처리하면 되는 경우, 한 스레드만 깨우는 게 더 효율적입니다.
        모든 스레드가 그 조건을 확인해야 할 때:
            notifyAll
            예를 들어, 여러 스레드가 자원을 기다리고 있을 때, 자원이 생겼다면 모든 스레드를 깨워서 처리하도록 해야 합니다.

        예시
            notify
                만약 생산자가 물건을 하나 만들면 소비자 중 한 명만 깨우고 그 소비자에게 물건을 주는 경우, notify()를 사용합니다.
            notifyAll
                만약 생산자가 많은 물건을 만들었고, 대기 중인 소비자 모두가 물건을 소비할 수 있는 상황이라면 notifyAll()을 사용하여 모든 소비자를 깨우고, 각 소비자가 자신의 조건에 맞게 물건을 가져가게 할 수 있습니다.
         */
    }

}
