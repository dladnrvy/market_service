package com.example.market_service.service;



import com.example.market_service.dto.GetFruitNameAndPriceDto;
import com.example.market_service.dto.GetVegetableNameAndPriceDto;
import com.example.market_service.repository.VegetableRepository;
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
public class VegetableService {

    private final VegetableRepository vegetableRepository;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     *  모든 야채 이름 목록 조회
     */
    public List<String> getAllVegetableName() {
        return vegetableRepository.findNameAll();
    }

    /**
     *  야채 이름, 가격 조회
     */
    public List<GetVegetableNameAndPriceDto> getVegetableNameAndPrice(String name) {
        return vegetableRepository.findNameAndPriceByName(name).stream().map(GetVegetableNameAndPriceDto::new).collect(Collectors.toList());
    }
}
