package com.nhnacademy.shoppingmall.pointHistory.repository;

import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;

public interface PointHistoryRepository {
    int save(PointHistory pointHistory);
}
