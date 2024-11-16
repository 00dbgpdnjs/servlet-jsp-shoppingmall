package com.nhnacademy.contoller;

import com.nhnacademy.domain.Inquiry;
import com.nhnacademy.service.InquiryService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerMainController.class)
class CustomerMainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InquiryService service;

    @Test
    void index() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    void detail() throws Exception {
        when(service.getInquiry(anyLong())).thenReturn(new Inquiry("test title", "test contenet", "test sort", "test userId", List.of("file1", "file2")));
        this.mockMvc.perform(get("/1")).andExpect(status().isOk());
    }
}