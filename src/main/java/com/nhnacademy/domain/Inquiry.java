package com.nhnacademy.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
public class Inquiry {

    @Setter
    private long id;

    private final String title;

    private final String content;
    private final LocalDateTime createdAt;
    private final String sort;
    private final String userId;
    private final List<String> filePathList;

//    @Setter
//    private String answerId;
    private String answer;
    private String manager;
    private LocalDateTime answeredAt;

    public Inquiry(String title, String content, String sort, String userId, List<String> filePathList) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.sort = sort;
        this.userId = userId;
        this.filePathList = filePathList;
    }

    public void answer(String answer, String manager) {
        this.answer = answer;
        this.manager = manager;
        this.answeredAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inquiry inquiry = (Inquiry) o;
        return id == inquiry.id && Objects.equals(title, inquiry.title) && Objects.equals(content, inquiry.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content);
    }
}
