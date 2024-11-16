package com.nhnacademy.contoller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogoutController.class)
class LogoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void logout() throws Exception {
        this.mockMvc.perform(get("/logout")).andExpect(status().is3xxRedirection());
    }
}