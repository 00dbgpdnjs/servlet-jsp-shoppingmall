package com.example.oauthsession.config;

import com.example.oauthsession.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {

        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 학습을 위해 disable
        http
                .csrf((csrf) -> csrf.disable());

        http
                .formLogin((login) -> login.disable());

        // 스프링 시큐리티에서는 HTTP Basic 인증이 기본적으로 활성화되어 있음
        // - ; 아래 코드 생략하면 생략하면 HTTP Basic 인증이 자동 활성화
        http
                .httpBasic((basic) -> basic.disable());

        // 3장 모식도의 필터와 세팅을 자동적으로 진행해줌
        // - 단, 아래 코드 넣으면 application.properties에 oauth2 변수 설정하지 않으면 실행 시 에러
        // 일단은 디폴트로
//        http
//                .oauth2Login(Customizer.withDefaults());
        // userInfoEndpoint: 우리가 데이터를 받을 수 있는 user detail service 를 등록할 endpoint
        http
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)));

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/oauth2/**", "/login/**").permitAll()
                        // 그 외 나머지 경로는 로그인 한 사람만 접근 가능
                        .anyRequest().authenticated());

        return http.build();
    }
}