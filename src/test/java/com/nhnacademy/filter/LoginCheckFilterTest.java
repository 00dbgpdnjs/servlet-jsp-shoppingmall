package com.nhnacademy.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginCheckFilterTest {
    private LoginCheckFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    @BeforeEach
    public void setUp() {
        filter = new LoginCheckFilter();
        request = mock(HttpServletRequest.class); // Mock 요청 객체
        response = mock(HttpServletResponse.class); // Mock 응답 객체
        chain = mock(FilterChain.class); // Mock 필터 체인 객체
    }

    @Test
    void doFilter() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/login");
        filter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilterNotLoginUrl() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/logout");
        filter.doFilter(request, response, chain);
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void doFilterWithSession() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/logout");
        when(request.getSession()).thenReturn(new MockHttpSession());
        filter.doFilter(request, response, chain);
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void doFilterWithSessionAndAttr() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/logout");
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute(any())).thenReturn((Object) "user");
        when(request.getSession(anyBoolean())).thenReturn(session);
        filter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }
}