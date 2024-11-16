package com.nhnacademy.service;

import com.nhnacademy.domain.Inquiry;
import com.nhnacademy.repositry.InquiryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class InquiryServiceTest {
    InquiryRepository inquiryRepository = Mockito.mock(InquiryRepository.class);

    InquiryService inquiryService =  new InquiryService(inquiryRepository);

    Inquiry testInquiry = new Inquiry("test title", "test contenet", "test sort", "test userId", List.of("file1", "file2"));

    @Test
    void addInquiry() {
        inquiryService.addInquiry(testInquiry);
        Mockito.verify(inquiryRepository,Mockito.times(1)).save(any());
    }

    @Test
    void getInquiries() {
        when(inquiryRepository.findByUserId(any())).thenReturn(List.of(testInquiry));
        List<Inquiry> inquiries = inquiryService.getInquiries("test userId");
        assertTrue(inquiries.contains(testInquiry));
    }

    @Test
    void getInquiriesWithNoAnswer() {
        when(inquiryRepository.findByAnswerIsNull()).thenReturn(List.of(testInquiry));
        List<Inquiry> inquiries = inquiryService.getInquiriesWithNoAnswer();
        assertTrue(inquiries.contains(testInquiry));

    }

    @Test
    void getInquiry() {
        when(inquiryRepository.findById(anyLong())).thenReturn(Optional.ofNullable(testInquiry));
        Inquiry inquiry = inquiryService.getInquiry(1);
        assertEquals(testInquiry, inquiry);
    }
}