package com.nhnacademy.contoller;

import com.nhnacademy.domain.Inquiry;
import com.nhnacademy.service.InquiryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ManagerMainController.class)
class ManagerMainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    InquiryService inquiryService;

    @BeforeEach
    void setUp() {
        Inquiry inquiry = new Inquiry("test title", "test contenet", "test sort", "test userId", List.of("file1", "file2"));
        when(inquiryService.getInquiry(anyLong())).thenReturn(inquiry);
    }

    @Test
    void index() throws Exception {
        this.mockMvc.perform(get("/admin")).andExpect(status().isOk());
    }

    @Test
    void answer() throws Exception {
        this.mockMvc.perform(get("/admin/1")).andExpect(status().isOk());
    }

    @Test
    void answerDo() throws Exception {
        mockMvc.perform(post("/admin/1")
                        .param("content", "test content")
                        .sessionAttr("id", "testUser")
                )
                .andExpect(status().is3xxRedirection());
    }
}