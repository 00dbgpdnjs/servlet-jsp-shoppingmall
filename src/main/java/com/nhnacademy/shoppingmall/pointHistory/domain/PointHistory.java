package com.nhnacademy.shoppingmall.pointHistory.domain;

public class PointHistory {
    private final String userId;
    private final int pointsUsed;

    public PointHistory(String userId, int pointsUsed) {
        this.userId = userId;
        this.pointsUsed = pointsUsed;
    }

    public String getUserId() {
        return userId;
    }

    public int getPointsUsed() {
        return pointsUsed;
    }

    @Override
    public String toString() {
        return "PointHistory{" +
                "userId='" + userId + '\'' +
                ", pointsUsed=" + pointsUsed +
                '}';
    }
}
