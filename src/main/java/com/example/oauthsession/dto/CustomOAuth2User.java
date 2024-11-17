package com.example.oauthsession.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2Response oAuth2Response;
    private final String role;

    public CustomOAuth2User(OAuth2Response oAuth2Response, String role) {

        this.oAuth2Response = oAuth2Response;
        this.role = role;
    }

    // 로그인을 진행하면 리소스 서버로 부터 넘어오는 모든 데이터
    // - 네이버랑 구글이랑 해당 데이터들의 키가 다르기 때문에 안 넣어주는게 낫을 것 같음
    @Override
    public Map<String, Object> getAttributes() {

        return null;
    }

    //role 값
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return role;
            }
        });

        return collection;
    }

    @Override
    public String getName() {

        return oAuth2Response.getName();
    }

    // oauth2로부터 받은 소셜 로그인 데이터에는 유저네임이라고 지칭할 수 있는게 없음
    //- 그래서 아이디값을 강제로 만들어줘야함
    public String getUsername() {

        return oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
    }
}