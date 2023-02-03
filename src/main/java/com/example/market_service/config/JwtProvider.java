package com.example.market_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtProvider {

    //토큰 생성
    public String createToken(String jwtSecretKey) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofDays(1).toMillis()); // 만료기간 1일

        return Jwts.builder()
                .setIssuedAt(now) // 발급시간(iat)
                .setExpiration(expiration) // 만료시간(exp)
                .setSubject("marketToken") //  토큰 제목(subject)
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey) // 알고리즘, 시크릿 키
                .compact();
    }

    //토큰의 유효성 체크
    public boolean parseJwtToken(String token, String jwtSecretKey) {
        token = BearerRemove(token);
        Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();
        return true;
    }

    //토큰 앞 부분('Bearer') 제거//
    private String BearerRemove(String token) {
        return token.substring("Bearer ".length());
    }

    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }
}
