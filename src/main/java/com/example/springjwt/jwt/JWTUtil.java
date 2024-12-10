package com.example.springjwt.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {


        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // 검증
    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public String getCategory(String token) {
        // Jwts.parser()는 파서 객체를 반환하고, 그 결과로 verifyWith() 같은 메서드를 호출 가능
        // verifyWith : JWT의 서명(Signature)을 검증해 토큰이 위조되지 않았는지 확인
        // build : 파서 인스턴스를 생성
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    // 발급
    /*단일 토큰
    public String createJwt(String username, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    } */

    // JWT 토큰 문자열을 반환
    public String createJwt(String category, String username, String role, Long expiredMs) {

        return Jwts.builder()
                // claim(String key, Object value): JWT의 Payload에 추가적인 정보를 저장
                .claim("category", category)
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                // JWT 토큰을 서명 : secretKey라는 비밀 키를 사용해 HMAC 알고리즘으로 토큰에 서명
                //  ;토큰의 무결성을 확인 ;토큰이 서버에서 생성된 것임을 검증
                .signWith(secretKey)
                .compact(); // 설정된 헤더(Header), 페이로드(Payload), 서명(Signature)이 포함된 JWT 토큰 문자열이 만들어짐
    }
}