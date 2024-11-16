package com.nhnacademy.repositry.impl;

import com.nhnacademy.domain.Inquiry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InquiryRepositoryImplTest {
    InquiryRepositoryImpl inquiryRepository = new InquiryRepositoryImpl();

    Inquiry testInquiry;

    @BeforeEach
    void setUp() {
        testInquiry = new Inquiry("test title", "test contenet", "test sort", "test userId", List.of("file1", "file2"));
        inquiryRepository.save(testInquiry);
    }

    @Test
    void findByUserId() {
        assertTrue(inquiryRepository.findByUserId(testInquiry.getUserId()).contains(testInquiry));
    }

    @Test
    void findByAnswerIsNull() {
        assertEquals(1, inquiryRepository.findByAnswerIsNull().size());
    }

    @Test
    void findById() {
        assertEquals(testInquiry, inquiryRepository.findById(1).get());
    }
}