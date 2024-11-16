package com.nhnacademy.repositry;

import com.nhnacademy.domain.Inquiry;

import java.util.List;
import java.util.Optional;

public interface InquiryRepository {
    void save(Inquiry inquiry);

    List<Inquiry> findByUserId(String userId);
    List<Inquiry> findByAnswerIsNull();

    Optional<Inquiry> findById(long id);
}
