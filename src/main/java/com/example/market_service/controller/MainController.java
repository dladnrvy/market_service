package com.example.market_service.controller;

import com.example.market_service.interceptor.NotAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/market")
public class MainController {

    @NotAuth
    @GetMapping("/main")
    public String main(){
        return "/main";
    }
}
