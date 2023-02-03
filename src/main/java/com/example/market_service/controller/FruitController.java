package com.example.market_service.controller;


import com.example.market_service.config.JwtProvider;
import com.example.market_service.dto.GetFruitNameAndPriceDto;
import com.example.market_service.dto.basic.BasicResponse;
import com.example.market_service.dto.basic.RtnCode;
import com.example.market_service.service.FruitService;
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

    private final FruitService fruitService;
    private final JwtProvider jwtProvider;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    //jwt key
    @Value("${fruit.jwt.password}")
    private String fruitSecretKey;

    @ApiOperation(value="과일가게 token 발급", notes="정상적으로 발급 될 경우 token 리턴")
    @GetMapping("/token")
    public ResponseEntity<BasicResponse> createToken(){
        BasicResponse rtn = new BasicResponse<>();
        String token = jwtProvider.createToken(fruitSecretKey); // 토큰 생성
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

        //토큰 유효성 체크
        jwtProvider.parseJwtToken(token, fruitSecretKey);
        if(StringUtils.isEmpty(name)){
            log.info("NAME 미존재");
            List<String> fruitNameList = fruitService.getAllFruitName();
            rtn.setData(fruitNameList);
        } else {
            log.info("NAME 존재");
            List<GetFruitNameAndPriceDto> fruitNameAndPriceList = fruitService.getFruitNameAndPrice(name);
            if(fruitNameAndPriceList.isEmpty()) rtn.setCode(RtnCode.INVALID_INPUT_VALUE);
            rtn.setData(fruitNameAndPriceList);
            return ResponseEntity.badRequest().body(rtn);
        }

        return ResponseEntity.ok(rtn);
    }





}
