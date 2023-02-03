package com.example.market_service.controller;


import com.example.market_service.config.JwtProvider;
import com.example.market_service.dto.GetVegetableNameAndPriceDto;
import com.example.market_service.dto.basic.BasicResponse;
import com.example.market_service.dto.basic.RtnCode;
import com.example.market_service.service.VegetableServiceImpl;
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
@RequestMapping("/vegetable")
public class VegetableController {

    private final VegetableServiceImpl vegetableService;
    private final JwtProvider jwtProvider;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    //jwt key
    @Value("${vegetable.jwt.password}")
    private String vegSecretKey;

    @ApiOperation(value="야채가게 token 발급", notes="정상적으로 발급 될 경우 token 리턴")
    @GetMapping("/token")
    public ResponseEntity<BasicResponse> createToken(){
        BasicResponse rtn = new BasicResponse<>();
        String token = jwtProvider.createToken(vegSecretKey); // 토큰 생성
        log.info("vegetable token : "+ token);
        rtn.setCode(RtnCode.SUCCESS);
        rtn.setData(token);
        return ResponseEntity.ok(rtn);
    }

    @ApiOperation(value="야채가게 목록조회", notes="정상적으로 조회 될 경우 야채 목록 리턴")
    @GetMapping("/product")
    public ResponseEntity<BasicResponse> getProduct(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(required = false) String name
    ) {
        BasicResponse rtn = new BasicResponse<>();
        rtn.setCode(RtnCode.SUCCESS);

        //토큰 유효성 체크
        jwtProvider.parseJwtToken(token, vegSecretKey);
        if(StringUtils.isEmpty(name)){
            log.info("NAME empty");
            List<String> vegetableNameList = vegetableService.getProductAllName();
            rtn.setData(vegetableNameList);
        } else {
            log.info("NAME not empty");
            List<GetVegetableNameAndPriceDto> vegetableNameAndPriceList = vegetableService.getProductNameAndPrice(name);
            if(vegetableNameAndPriceList.isEmpty()){
                rtn.setCode(RtnCode.INVALID_INPUT_VALUE);
                return ResponseEntity.badRequest().body(rtn);
            }
            rtn.setData(vegetableNameAndPriceList);
        }

        return ResponseEntity.ok(rtn);
    }





}
