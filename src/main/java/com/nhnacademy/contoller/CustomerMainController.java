package com.nhnacademy.contoller;

import com.nhnacademy.domain.Inquiry;
import com.nhnacademy.service.InquiryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class CustomerMainController {
    private final InquiryService inquiryService;

    @GetMapping
    public String index(HttpSession session,
                        Model model) {
        model.addAttribute("inquiries",
                inquiryService.getInquiries((String) session.getAttribute("id")));
        return "customerMain";
    }

    @GetMapping("/{inquiryId:[0-9]+}")
    public String detail(@PathVariable("inquiryId") String inquiryId, Model model) {
        Inquiry inquiry = inquiryService.getInquiry(Long.parseLong(inquiryId));
        model.addAttribute("inquiry", inquiry);
        return "inquiryDetail";
    }
}
