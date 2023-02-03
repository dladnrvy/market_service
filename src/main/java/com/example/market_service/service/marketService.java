package com.example.market_service.service;

import com.example.market_service.dto.GetVegetableNameAndPriceDto;

import java.util.List;

public interface marketService<T, R> {

    List<T> getProductAllName();
    List<T> getProductNameAndPrice(R r);
}
