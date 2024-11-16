package com.nhnacademy.service;

import com.nhnacademy.domain.Inquiry;
import com.nhnacademy.repositry.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepository;

    public void addInquiry(Inquiry inquiry) {
        inquiryRepository.save(inquiry);
    }

    public List<Inquiry> getInquiries(String userId) {
        return inquiryRepository.findByUserId(userId);
    }

    public List<Inquiry> getInquiriesWithNoAnswer() {
        return inquiryRepository.findByAnswerIsNull();
    }

    public Inquiry getInquiry(long inquiryId) {
        Optional<Inquiry> inquiryOptional = inquiryRepository.findById(inquiryId);
        if(inquiryOptional.isEmpty()) throw new RuntimeException(String.format("inquiry id %s not found", inquiryId));
        return inquiryOptional.get();
    }
}
