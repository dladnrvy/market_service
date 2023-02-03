package com.example.market_service.exception;


import com.example.market_service.dto.basic.BasicResponse;
import com.example.market_service.dto.basic.RtnCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler
    public BasicResponse errorHandler(Exception e) {
        BasicResponse rtn = new BasicResponse<>();
        rtn.setCode(RtnCode.FAIL);
        rtn.setData(e.getMessage());
        return rtn;
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse MissingRequestHeaderException(Exception e) {
        e.printStackTrace();
        return new BasicResponse("400", "MissingRequestHeaderException");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse UnsupportedJwtException(Exception e) {
        e.printStackTrace();
        return new BasicResponse("401", "UnsupportedJwtException");
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse MalformedJwtException(Exception e) {
        e.printStackTrace();
        return new BasicResponse("402", "MalformedJwtException");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse ExpiredJwtException(Exception e) {
        e.printStackTrace();
        return new BasicResponse("403", "ExpiredJwtException");
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse SignatureException(Exception e) {
        e.printStackTrace();
        return new BasicResponse("404", "SignatureException");
    }

}
