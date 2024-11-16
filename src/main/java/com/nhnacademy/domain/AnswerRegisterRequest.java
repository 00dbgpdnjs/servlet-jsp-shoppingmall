package com.nhnacademy.domain;

import jakarta.validation.constraints.Size;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Value
public class AnswerRegisterRequest {
    @Size(min = 1, max = 40000)
    String content;
}
