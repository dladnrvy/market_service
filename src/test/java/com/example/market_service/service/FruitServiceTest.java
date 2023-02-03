package com.example.market_service.service;

import com.example.market_service.domain.FruitEntity;
import com.example.market_service.dto.GetFruitNameAndPriceDto;
import com.example.market_service.repository.FruitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FruitServiceTest {

    @InjectMocks
    private FruitService fruitService;

    @Mock
    private FruitRepository fruitRepository;

    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private String NAME = "사과";
    private Integer PRICE = 1000;
    private FruitEntity fruitEntity = FruitEntity.builder().name(NAME).price(PRICE).build();


    @Nested
    @DisplayName("성공케이스")
    class SuccessCase{
        @Test
        public void 모든_과일이름_조회(){
            // given
            List<String> fakeFruitList = new ArrayList<>();
            fakeFruitList.add("사과");
            fakeFruitList.add("배");

            // mocking
            given(fruitRepository.findNameAll()).willReturn(fakeFruitList);

            // when
            List<String> findFruitNameList = fruitService.getAllFruitName();

            // then
            assertThat(findFruitNameList).isNotEmpty();
            assertThat(findFruitNameList.size()).isEqualTo(2);
            assertEquals(findFruitNameList.get(0), "사과");
            assertEquals(findFruitNameList.get(1), "배");
        }

        @Test
        public void 과일_이름_가격_조회(){
            // given
            List<FruitEntity> fakeFruitList = new ArrayList<>();
            fakeFruitList.add(fruitEntity);

            // mocking
            given(fruitRepository.findNameAndPriceByName(NAME)).willReturn(fakeFruitList);

            // when
            List<GetFruitNameAndPriceDto> findFruitNameList = fruitService.getFruitNameAndPrice(NAME);

            // then
            assertThat(findFruitNameList).isNotEmpty();
            assertThat(findFruitNameList.size()).isEqualTo(1);
            assertEquals(fruitEntity.getName(), findFruitNameList.get(0).getName());
            assertEquals(fruitEntity.getPrice(), findFruitNameList.get(0).getPrice());
        }

    }

    @Nested
    @DisplayName("실패케이스")
    class FailCase {
        @Test
        public void 모든_과일이름_조회(){
            // given
            List<String> fakeFruitList = new ArrayList<>();

            // mocking
            given(fruitRepository.findNameAll()).willReturn(fakeFruitList);

            // when
            List<String> findFruitNameList = fruitService.getAllFruitName();

            // then
            assertThat(findFruitNameList).isEmpty();
        }

        @Test
        public void 과일_이름_가격_조회(){
            // given
            List<FruitEntity> fakeFruitList = new ArrayList<>();

            // mocking
            given(fruitRepository.findNameAndPriceByName(NAME)).willReturn(fakeFruitList);

            // when
            List<GetFruitNameAndPriceDto> findFruitNameList = fruitService.getFruitNameAndPrice(NAME);

            // then
            assertThat(findFruitNameList).isEmpty();
        }
    }
}