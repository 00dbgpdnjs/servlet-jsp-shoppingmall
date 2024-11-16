package com.nhnacademy.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InquiryTest {
    Inquiry testInquiry = new Inquiry("test title", "test contenet", "test sort", "test userId", List.of());

    @Test
    void testNotEqualTitle() {
        Inquiry testInquiry2 = new Inquiry("test title2", "test contenet2", "test sort2", "test userId2", List.of());
        Assertions.assertNotEquals(testInquiry, testInquiry2);
    }

    @Test
    void testNotEqualsId() {
        Inquiry testInquiry2 = new Inquiry("test title2", "test contenet2", "test sort2", "test userId2", List.of());
        testInquiry2.setId(2);
        Assertions.assertNotEquals(testInquiry, testInquiry2);
    }

    @Test
    void testwithNull() {
        Assertions.assertNotEquals(testInquiry, null);
    }

    @Test
    void testEqualsWithOtherClass() {
        User user = new User("id", "pw");
        Assertions.assertNotEquals(testInquiry, user);
    }
}