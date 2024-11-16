package com.nhnacademy.domain;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Value // ?? 이거 안하면 유효성 검사 안하는 이유
public class InquiryRegisterRequest {
    @NotBlank
    String sort;
    @Size(min = 2, max = 200)
    String title;
    @Size(max = 40000)
    String content;
    List<MultipartFile> files;
}
