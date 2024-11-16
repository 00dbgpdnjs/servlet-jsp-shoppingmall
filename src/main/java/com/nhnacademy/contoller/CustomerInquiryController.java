package com.nhnacademy.contoller;

import com.nhnacademy.domain.Inquiry;
import com.nhnacademy.domain.InquiryRegisterRequest;
import com.nhnacademy.domain.InquirySort;
import com.nhnacademy.exception.ValidationFailedException;
import com.nhnacademy.service.InquiryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class CustomerInquiryController {
    private final InquiryService inquiryService;

    @GetMapping
    public String inquiry() {
        return "inquiryForm";
    }

    @PostMapping
//      @ModelAttribute: 폼 데이터의 name 속성과 일치하는 자바 객체의 필드에 자동으로 매핑
    public String submit(HttpSession session, // 세션이 없을 경우, 새로운 세션을 자동으로 생성하여 해당 세션 객체를 메소드에 주입
                         @Valid @ModelAttribute InquiryRegisterRequest inquiryReq,
                         BindingResult bindingResult,
                         @Value("${upload.dir}") String uploadDir) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        if(Objects.isNull(InquirySort.fromString(inquiryReq.getSort())))
            throw new RuntimeException(String.format("Invalid sort value %s", inquiryReq.getSort()));

        List<String> filePathList = new LinkedList<>();
        if(Objects.nonNull(inquiryReq.getFiles())){
            for(MultipartFile file : inquiryReq.getFiles()) {
                if (file.isEmpty()) { //|| file.getOriginalFilename().isEmpty()) { //file.isEmpty(): 파일이 비어 있는 경우(즉, 파일이 없거나, 크기가 0인 경우)
                    continue; // 빈 파일은 건너뛰기
                }
                // 동일한 이름의 파일이 이미 존재한다면, 해당 파일이 덮어쓰기 되거나 예외가 발생
                file.transferTo(Paths.get(uploadDir + file.getOriginalFilename()));
                filePathList.add(file.getOriginalFilename());
            }
        }

        Inquiry inquiry = new Inquiry(inquiryReq.getTitle(), inquiryReq.getContent(), inquiryReq.getSort(), (String) session.getAttribute("id"), filePathList);
        inquiryService.addInquiry(inquiry);

        return "redirect:/";
    }
}
