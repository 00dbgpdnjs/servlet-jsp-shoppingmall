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

    public String getDescription() {
        return description;
    }

    // Enum 값을 문자열로 변환 (주로 바인딩이나 출력용)
    @Override
    public String toString() {
        return this.description;
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
