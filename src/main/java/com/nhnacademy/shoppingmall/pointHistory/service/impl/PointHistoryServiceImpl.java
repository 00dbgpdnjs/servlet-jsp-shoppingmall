package com.nhnacademy.shoppingmall.pointHistory.service.impl;

import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;
import com.nhnacademy.shoppingmall.pointHistory.repository.PointHistoryRepository;
import com.nhnacademy.shoppingmall.pointHistory.service.PointHistoryService;

public class PointHistoryServiceImpl implements PointHistoryService {
    private  final PointHistoryRepository pointHistoryRepository;

    public PointHistoryServiceImpl(PointHistoryRepository pointHistoryRepository) {
        this.pointHistoryRepository = pointHistoryRepository;
    }

    @Override
    public void savePointHistory(PointHistory pointHistory) {
        int result = pointHistoryRepository.save(pointHistory);

        // ?? 첨에 if문 했으니까 생략 (update, del 등 다른 메서드도)
        if(result<1){
            throw new RuntimeException("fail-savePointHistory:" + pointHistory);
        }
    }
}
