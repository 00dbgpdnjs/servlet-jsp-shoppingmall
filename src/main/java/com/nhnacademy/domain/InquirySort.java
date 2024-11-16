package com.nhnacademy.domain;

public enum InquirySort {
    COMPLAINT("불만 접수"),
    SUGGESTION("제안"),
    REFUND_EXCHANGE("환불/교환"),
    PRAISE("칭찬해요"),
    OTHER("기타 문의");

    private final String description;

    InquirySort(String description) {
        this.description = description;
    }

    // 문자열을 enum으로 변환하는 메서드
    public static InquirySort fromString(String text) {
        for (InquirySort sort : InquirySort.values()) {
            if (sort.description.equalsIgnoreCase(text)) {
                return sort;
            }
        }
        return null;
    }
}
