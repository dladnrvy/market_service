package com.example.market_service.controller;


import com.example.market_service.config.JwtProvider;
import com.example.market_service.dto.basic.BasicResponse;
import com.example.market_service.dto.basic.RtnCode;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fruit")
public class FruitController {

//    private final PointServiceImpl pointService;
//    private final PointResultServiceImpl pointResultService;
    private final JwtProvider jwtProvider;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());


    @ApiOperation(value="과일가게 token 발급", notes="정상적으로 발급 될 경우 token 리턴")
    @GetMapping("/token")
    public ResponseEntity<BasicResponse> createToken(){
        BasicResponse rtn = new BasicResponse<>();
        String token = jwtProvider.createToken(); // 토큰 생성
        rtn.setCode(RtnCode.SUCCESS);
        rtn.setData(token);
        return ResponseEntity.ok(rtn);
    }

    @ApiOperation(value="과일가게 목록조회", notes="정상적으로 조회 될 경우 과일 목록 리턴")
    @GetMapping("/product")
    public ResponseEntity<BasicResponse> getProduct(@RequestHeader(value = "Authorization") String token){
        BasicResponse rtn = new BasicResponse<>();
        //토큰 유효성 체크
        jwtProvider.parseJwtToken(token);


        rtn.setCode(RtnCode.SUCCESS);
        rtn.setData(token);
        return ResponseEntity.ok(rtn);
    }





}
