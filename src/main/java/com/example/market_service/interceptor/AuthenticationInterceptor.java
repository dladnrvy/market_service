package com.example.market_service.interceptor;

import com.example.market_service.config.JwtProvider;
import com.example.market_service.exception.NotFonudTokenException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtProvider jwtProvider;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private String fruitSecretKey;
    private String vegSecretKey;

    public AuthenticationInterceptor(JwtProvider jwtProvider, String fruitSecretKey, String vegSecretKey) {
        this.jwtProvider = jwtProvider;
        this.fruitSecretKey = fruitSecretKey;
        this.vegSecretKey = vegSecretKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean check=checkAnnotation(handler, NotAuth.class);
        if(check) return true;
            String requestURI= request.getRequestURI();
            String key = "";

            if (requestURI.contains("/fruit/")){
                key = fruitSecretKey;
            }else if(requestURI.contains("/vegetable/")){
                key = vegSecretKey;
            }else{
                return false;
            }

            String accessToken = jwtProvider.getJwt();
            if(accessToken == null) throw new NotFonudTokenException();

            //token 확인
            boolean tokenChk = jwtProvider.parseJwtToken(accessToken, key);
            if(!tokenChk) return false;

        return true;
    }

    private boolean checkAnnotation(Object handler,Class cls){
        HandlerMethod handlerMethod=(HandlerMethod) handler;
        if(handlerMethod.getMethodAnnotation(cls)!=null){ //해당 어노테이션이 존재하면 true.
            return true;
        }
        return false;
    }
}
