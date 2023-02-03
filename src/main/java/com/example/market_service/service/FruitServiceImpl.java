package com.example.market_service.service;



import com.example.market_service.dto.GetFruitNameAndPriceDto;
import com.example.market_service.repository.FruitRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FruitServiceImpl implements marketService{

    private final FruitRepository fruitRepository;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());


    /**
     *  모든 과일 이름 목록 조회
     */
    @Override
    public List<String> getProductAllName() {
        return fruitRepository.findNameAll();
    }

    /**
     *  과일 이름, 가격 조회
     */
    @Override
    public List<GetFruitNameAndPriceDto> getProductNameAndPrice(Object name) {
        return fruitRepository.findNameAndPriceByName((String) name).stream().map(GetFruitNameAndPriceDto::new).collect(Collectors.toList());
    }
}
