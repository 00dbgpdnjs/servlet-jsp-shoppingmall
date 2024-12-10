package com.nhnacademy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        // 프론트엔드 애플리케이션이 백엔드 API("/**")에 자유롭게 접근 가능
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:3000");
    }
}