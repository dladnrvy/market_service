package com.example.market_service.service;

import com.example.market_service.domain.VegetableEntity;
import com.example.market_service.dto.GetVegetableNameAndPriceDto;
import com.example.market_service.repository.VegetableRepository;
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
class VegetableServiceTest {

    @InjectMocks
    private VegetableServiceImpl vegetableService;

    @Mock
    private VegetableRepository vegetableRepository;

    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private String NAME = "치커리";
    private Integer PRICE = 1000;
    private VegetableEntity vegetableEntity = VegetableEntity.builder().name(NAME).price(PRICE).build();

    @Nested
    @DisplayName("성공케이스")
    class SuccessCase{
        @Test
        public void 모든_야채이름_조회(){
            // given
            List<String> fakeVegetableList = new ArrayList<>();
            fakeVegetableList.add("치커리");
            fakeVegetableList.add("깻잎");

            // mocking
            given(vegetableRepository.findNameAll()).willReturn(fakeVegetableList);

            // when
            List<String> findVegetableNameList = vegetableService.getProductAllName();

            // then
            assertThat(findVegetableNameList).isNotEmpty();
            assertThat(findVegetableNameList.size()).isEqualTo(2);
            assertEquals(findVegetableNameList.get(0), "치커리");
            assertEquals(findVegetableNameList.get(1), "깻잎");
        }

        @Test
        public void 야채_이름_가격_조회(){
            // given
            List<VegetableEntity> fakeVegetableList = new ArrayList<>();
            fakeVegetableList.add(vegetableEntity);

            // mocking
            given(vegetableRepository.findNameAndPriceByName(NAME)).willReturn(fakeVegetableList);

            // when
            List<GetVegetableNameAndPriceDto> findVegNameAndPriceList = vegetableService.getProductNameAndPrice(NAME);

            // then
            assertThat(findVegNameAndPriceList).isNotEmpty();
            assertThat(findVegNameAndPriceList.size()).isEqualTo(1);
            assertEquals(vegetableEntity.getName(), findVegNameAndPriceList.get(0).getName());
            assertEquals(vegetableEntity.getPrice(), findVegNameAndPriceList.get(0).getPrice());
        }

    }

    @Nested
    @DisplayName("실패케이스")
    class FailCase {
        @Test
        public void 모든_과일이름_조회(){
            // given
            List<String> fakeVegetableList = new ArrayList<>();

            // mocking
            given(vegetableRepository.findNameAll()).willReturn(fakeVegetableList);

            // when
            List<String> findVegetableNameList = vegetableService.getProductAllName();

            //then
            assertThat(findVegetableNameList).isEmpty();
        }

        @Test
        public void 과일_이름_가격_조회(){
            // given
            List<VegetableEntity> fakeVegetableList = new ArrayList<>();

            // mocking
            given(vegetableRepository.findNameAndPriceByName(NAME)).willReturn(fakeVegetableList);

            // when
            List<GetVegetableNameAndPriceDto> findVegNameAndPriceList = vegetableService.getProductNameAndPrice(NAME);

            // then
            assertThat(findVegNameAndPriceList).isEmpty();
        }
    }
}