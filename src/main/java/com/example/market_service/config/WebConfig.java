package com.example.market_service.config;

import com.example.market_service.interceptor.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig  implements WebMvcConfigurer {

    private final JwtProvider jwtProvider;
    @Value("${fruit.jwt.password}")
    private String fruitSecretKey;
    @Value("${vegetable.jwt.password}")
    private String vegSecretKey;

    @Override
    public void addInterceptors(InterceptorRegistry reg){
        reg.addInterceptor(new AuthenticationInterceptor(jwtProvider, fruitSecretKey, vegSecretKey));
    }
}
