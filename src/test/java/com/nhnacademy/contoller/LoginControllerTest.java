package com.nhnacademy.contoller;

import com.nhnacademy.domain.Customer;
import com.nhnacademy.domain.Manager;
import com.nhnacademy.repositry.CustomerRepository;
import com.nhnacademy.repositry.ManagerRepository;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    /*Parameter 1 of constructor in com.nhnacademy.controller.LoginController required a bean of type 'com.nhnacademy.repository.ManagerRepository' that could not be found.
    - LoginController의 생성자에서 ManagerRepository를 주입받으려고 하는데, Spring 컨테이너에서 ManagerRepository 빈을 찾을 수 없어서 발생하는 문제
    - LoginController 생성자는 테스트 실행 시점에 호출
        MockMvc를 사용하여 테스트를 실행하기 전에 LoginController가 테스트 컨텍스트에 로드되며, 이때 생성자가 호출되어 필요한 의존성들이 주입됩니다.
     */
    @MockBean
    private ManagerRepository managerRepository;

    @Test
    void login() throws Exception {
        this.mockMvc.perform(get("/login")).andExpect(status().isOk());
    }

    @Test
    void loginWithSession() throws Exception {
        this.mockMvc.perform(get("/login")
                .sessionAttr("id", "testUser"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void doLogin() throws Exception {
        Customer expect = new Customer("1", "1234", "tom");

        when(customerRepository.findById(any())).thenReturn(Optional.of(expect));
        mockMvc.perform(post("/login")
                        .param("id", "1")
                        .param("pwd", "1234")
                )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void CustomerloginFail() throws Exception {
        Customer expect = new Customer("1", "1234", "tom");

        when(customerRepository.findById(any())).thenReturn(Optional.of(expect));

        Throwable th = catchThrowable(() ->
                        mockMvc.perform(post("/login")
                                .param("id", "1")
                                .param("pwd", "123456")
                        ));

        assertThat(th).isInstanceOf(ServletException.class)
                .hasCauseInstanceOf(RuntimeException.class);
    }

    @Test
    void doManagerLogin() throws Exception {
        Manager expect = new Manager("tom", "1234");

        when(managerRepository.findById(any())).thenReturn(Optional.of(expect));
        mockMvc.perform(post("/login")
                        .param("id", "tom")
                        .param("pwd", "1234")
                )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void doManagerLoginFail() throws Exception {
        Throwable th = catchThrowable(() ->
                mockMvc.perform(post("/login")
                        .param("id", "tom")
                        .param("pwd", "1234")
                ));

        assertThat(th).isInstanceOf(ServletException.class)
                .hasCauseInstanceOf(RuntimeException.class);
    }

    @Test
    void doManagerLoginPwdFail() throws Exception {
        Manager expect = new Manager("tom", "1234");

        when(managerRepository.findById(any())).thenReturn(Optional.of(expect));
        Throwable th = catchThrowable(() ->
                mockMvc.perform(post("/login")
                        .param("id", "tom")
                        .param("pwd", "123456")
                ));

        assertThat(th).isInstanceOf(ServletException.class)
                .hasCauseInstanceOf(RuntimeException.class);
    }


    @Test
    void doLoginWithoutBlankId() throws Exception {
        Throwable th = catchThrowable(() ->
                        mockMvc.perform(post("/login")
                                .param("id", "")
                                .param("pwd", "1234")
                        ));

        assertThat(th).isInstanceOf(ServletException.class)
                .hasCauseInstanceOf(RuntimeException.class);
    }
    @Test
    void doLoginWithoutBlankPwd() throws Exception {
        Throwable th = catchThrowable(() ->
                mockMvc.perform(post("/login")
                        .param("id", "1")
                        .param("pwd", "")
                ));

        assertThat(th).isInstanceOf(ServletException.class)
                .hasCauseInstanceOf(RuntimeException.class);
    }
}