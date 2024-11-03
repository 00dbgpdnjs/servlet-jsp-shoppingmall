package com.nhnacademy.shoppingmall.pointHistory.service;

import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;

import java.util.List;

public interface PointHistoryService {
    void savePointHistory(PointHistory pointHistory);
    List<PointHistory> getHistoryService(int page, String userId);
    int getCountByUserId(String userId);
}
