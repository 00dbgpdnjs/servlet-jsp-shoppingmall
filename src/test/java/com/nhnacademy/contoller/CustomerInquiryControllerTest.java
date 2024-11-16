package com.nhnacademy.contoller;

import com.nhnacademy.exception.ValidationFailedException;
import com.nhnacademy.service.InquiryService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerInquiryController.class)
//@ContextConfiguration(classes = {InquiryService.class})
class CustomerInquiryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /* @Mock 으로 하면 에러가 나는 이유
    - @Mock (Mockito)
        - Spring Context에 빈(bean)을 등록하지 않기 때문에, Spring의 IoC 컨테이너와 상호작용하는 경우에는 사용될 수 없습니다.
          ;Spring 컨텍스트에 InquiryService 객체가 등록되지 않기 때문에, CustomerInquiryController의 InquiryService 필드를 주입할 수 없습니다.
    - @MockBean (Spring Test)
        - Spring의 IoC 컨테이너에 mock 객체를 등록

    ?? MockMvcBuilders.standaloneSetup(new CustomerInquiryController(service))
       service 를 직접 주입하니까 @Autowired를 통해 의존성 주입을 받는게 아니지 않나?
        -> 맞음. WebMvcTest를 했으니까 standaloneSetup 할 필요가 없었음,,,
     */
    @MockBean
    private InquiryService service;

    @Test
    void inquiry() throws Exception {
        this.mockMvc.perform(get("/inquiry")).andExpect(status().isOk());
    }

    @Test
    void submit() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "files", // 파라미터 이름
                "testfile.txt", // 파일 이름
                "text/plain", // MIME 타입
                "This is a test file content.".getBytes() // 파일 내용
        );

        mockMvc.perform(multipart("/inquiry")
                        .file(file) // 업로드할 파일
                        .param("sort", "제안")
                        .param("title", "test title")
                        .param("content", "test content")
                )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void submitBidingFail(){
        Throwable th = catchThrowable(() ->
                mockMvc.perform(multipart("/inquiry")
                        .param("title", "test title")));

        assertThat(th).isInstanceOf(ServletException.class)
                .hasCauseInstanceOf(ValidationFailedException.class);

    }


    @Test
    void submitSortFail() throws Exception {
        Throwable th = catchThrowable(() ->
                mockMvc.perform(multipart("/inquiry")
                        .param("sort", "제안!!")
                        .param("title", "test title")
                        .param("content", "test content")
                ));

        assertThat(th).isInstanceOf(ServletException.class)
                .hasCauseInstanceOf(RuntimeException.class);
    }

    @Test
    void submitWithoutFile() throws Exception {
        mockMvc.perform(multipart("/inquiry")
                        .param("sort", "제안")
                        .param("title", "test title")
                        .param("content", "test content")
                )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void submitWithEmptyFild() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "files",
                "testfile.txt",
                "text/plain",
                "".getBytes()
        );

        mockMvc.perform(multipart("/inquiry")
                        .file(file)
                        .param("sort", "제안")
                        .param("title", "test title")
                        .param("content", "test content")
                )
                .andExpect(status().is3xxRedirection());
    }
}