package com.example.oauthsession.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Component;

@Component
public class SocialClientRegistration {

    @Value("${naver.client-id}")
    String naverClientId;
    @Value("${naver.client-secret}")
    String naverClientSecret;

    public ClientRegistration naverClientRegistration() {

        return ClientRegistration.withRegistrationId("naver")
                .clientId(naverClientId)
                .clientSecret(naverClientSecret)
                .redirectUri("http://localhost:8080/login/oauth2/code/naver")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("name", "email")
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .build();
    }

//    public ClientRegistration googleClientRegistration() {
//
//        return ClientRegistration.withRegistrationId("google")
//                .clientId("아이디")
//                .clientSecret("비밀번호")
//                .redirectUri("http://localhost:8080/login/oauth2/code/google")
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .scope("profile", "email")
//    // 주의: properties에서 설정할 때는 구글 같은 경우에 provide를 세팅안해도 됐었는데 커스텀으로 생성할 때는 넣어줘야함
//                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
//                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
//                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
//                .issuerUri("https://accounts.google.com")
//                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
//                .userNameAttributeName(IdTokenClaimNames.SUB)
//                .build();
//    }
}