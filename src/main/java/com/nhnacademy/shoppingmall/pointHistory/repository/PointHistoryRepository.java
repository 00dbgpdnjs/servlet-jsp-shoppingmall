package com.nhnacademy.shoppingmall.pointHistory.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;

public interface PointHistoryRepository {
    int save(PointHistory pointHistory);
    int countByUserId(String userId);
    Page<PointHistory> findByUserId(int page, int pageSize, String userId);
}
