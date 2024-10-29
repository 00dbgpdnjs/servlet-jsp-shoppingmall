package com.nhnacademy.shoppingmall.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(
        urlPatterns = "*.do",
        initParams = {
                @WebInitParam(name = "encoding",value = "UTF-8")
        }
)
public class CharacterEncodingFilter  implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 매개변수에서 인코딩 값 가져오기
        encoding = filterConfig.getInitParameter("encoding");
        if (encoding == null) {
            encoding = "UTF-8"; // 기본값 설정
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //todo#8 UTF-8 인코딩, initParams의 encoding parameter value값을 charset 으로 지정합니다.
        //@WebFilter(initParams = {@WebInitParam(name = "encoding",value = "UTF-8")})
        /* FilterConfig.getInitParameter vs ServletContext.getInitParameter
            - 전자: 필터에 대한 초기화 매개변수
            - 후자: 애플리케이션 전체에 대한 초기화 매개변수를 다루며, 여러 서블릿과 필터에서 공유할 수 있습니다.
                web.xml 파일을 통해 정의된 매개변수를 읽을 때
                여러 컴포넌트에서 공유하고자 하는 매개변수
            + ServletConfig: 서블릿에 대한 초기화 매개변수를 다루며, 서블릿의 생명 주기에 맞춰 설정됩니다.
         */
//        String encoding = servletRequest.getServletContext().getInitParameter("encoding");
//        log.debug("Character encoding : {}", encoding);
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
