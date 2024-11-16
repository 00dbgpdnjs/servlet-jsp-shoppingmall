package com.nhnacademy.repositry.impl;

import com.nhnacademy.domain.Inquiry;
import com.nhnacademy.repositry.InquiryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.function.Function;

@Repository
public class InquiryRepositoryImpl implements InquiryRepository {
    private final Map<Long, Inquiry> inquirys = new HashMap<>();

    @Override
    public void save(Inquiry inquiry) {
        long id = inquirys.keySet()
                .stream()
                .max(Comparator.comparing(Function.identity()))
                .map(l -> l + 1)
                .orElse(1L);

        inquiry.setId(id);

        inquirys.put(id, inquiry);
    }

    @Override
    public List<Inquiry> findByUserId(String userId) {
        return inquirys.values().stream()
                .filter(inquiry -> inquiry.getUserId().equals(userId))
                .sorted(Comparator.comparing(Inquiry::getCreatedAt).reversed())
                .toList(); // collect(Collectors.toList())와 결과는 동일
    }

    @Override
    public List<Inquiry> findByAnswerIsNull() {
        return inquirys.values().stream()
                .filter(inquiry -> Objects.isNull(inquiry.getAnswer()))
                .toList();
    }

    @Override
    public Optional<Inquiry> findById(long id) {
        return Optional.ofNullable(inquirys.get(id));
    }
}
