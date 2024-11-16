package com.nhnacademy.contoller;

import com.nhnacademy.domain.AnswerRegisterRequest;
import com.nhnacademy.domain.Inquiry;
import com.nhnacademy.exception.ValidationFailedException;
import com.nhnacademy.service.InquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ManagerMainController {
    private final InquiryService inquiryService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("inquiries", inquiryService.getInquiriesWithNoAnswer());
        return "managerMain";
    }

    @GetMapping("/{inquiryId:[0-9]+}")
    public String answer(@PathVariable("inquiryId") String inquiryId,
                         @Value("${upload.dir}") String uploadDir,
                         Model model) {
        Inquiry inquiry = inquiryService.getInquiry(Long.parseLong(inquiryId));

        model.addAttribute("inquiry", inquiry);
        model.addAttribute("uploadDir", uploadDir);

        return "answerForm";
    }

    @PostMapping("/{inquiryId:[0-9]+}")
    public String answerDo(@PathVariable("inquiryId") String inquiryId,
                           @Valid @ModelAttribute AnswerRegisterRequest answerReq,
                           BindingResult bindingResult,
                           HttpServletRequest req){
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Inquiry inquiry = inquiryService.getInquiry(Long.parseLong(inquiryId));
        inquiry.answer(answerReq.getContent(), (String) req.getSession(false).getAttribute("id"));
        return "redirect:/admin";
    }
}
