package com.example.market_service.dto.basic;

import lombok.Data;

@Data
public class RtnCode {
    public final static String SUCCESS = "200";
    public final static String FAIL = "404";
    public final static String INVALID_INPUT_VALUE = "400";
}
