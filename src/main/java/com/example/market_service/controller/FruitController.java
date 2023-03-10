package com.example.market_service.controller;


import com.example.market_service.config.JwtProvider;
import com.example.market_service.dto.GetFruitNameAndPriceDto;
import com.example.market_service.dto.basic.BasicResponse;
import com.example.market_service.dto.basic.RtnCode;
import com.example.market_service.interceptor.NotAuth;
import com.example.market_service.service.FruitServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fruit")
public class FruitController {

    private final FruitServiceImpl fruitService;
    private final JwtProvider jwtProvider;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    //jwt key
    @Value("${fruit.jwt.password}")
    private String fruitSecretKey;

    @NotAuth
    @ApiOperation(value="과일가게 token 발급", notes="정상적으로 발급 될 경우 token 리턴")
    @GetMapping("/token")
    public ResponseEntity<BasicResponse> createToken(){
        BasicResponse rtn = new BasicResponse<>();
        String token = jwtProvider.createToken(fruitSecretKey); // 토큰 생성
        log.info("fruit token : "+ token);
        rtn.setCode(RtnCode.SUCCESS);
        rtn.setData(token);
        return ResponseEntity.ok(rtn);
    }

    @ApiOperation(value="과일가게 목록조회", notes="정상적으로 조회 될 경우 과일 목록 리턴")
    @GetMapping("/product")
    public ResponseEntity<BasicResponse> getProduct(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(required = false) String name
    ) {
        BasicResponse rtn = new BasicResponse<>();
        rtn.setCode(RtnCode.SUCCESS);

        if(StringUtils.isEmpty(name)){
            log.info("NAME empty");
            List<String> fruitNameList = fruitService.getProductAllName();
            rtn.setData(fruitNameList);
        } else {
            log.info("NAME not empty");
            List<GetFruitNameAndPriceDto> fruitNameAndPriceList = fruitService.getProductNameAndPrice(name);
            if(fruitNameAndPriceList.isEmpty()){
                rtn.setCode(RtnCode.INVALID_INPUT_VALUE);
                return ResponseEntity.badRequest().body(rtn);
            }
            rtn.setData(fruitNameAndPriceList);
        }

        return ResponseEntity.ok(rtn);
    }





}
