package com.example.market_service.repository;

import com.example.market_service.domain.FruitEntity;
import com.example.market_service.domain.VegetableEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Transactional
@DataJpaTest
class VegetableRepositoryTest {
    @Autowired
    private VegetableRepository vegetableRepository;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private VegetableEntity vegetableEntity;

    private String NAME = "양파";
    private Integer PRICE = 1000;

    //    초기화
    @BeforeEach
    public void setUp() {
        vegetableEntity = VegetableEntity.builder()
                .name(NAME)
                .price(PRICE)
                .build();
    }

    @Nested
    @DisplayName("성공케이스")
    class SuccessCase{

        @Nested
        @DisplayName("야채가게_저장_성공케이스")
        class SaveSuccessCase{

            @Test
            public void 야채_저장(){
                // given
                VegetableEntity nextVegetableEntity = VegetableEntity.builder()
                        .name("파")
                        .price(2000)
                        .build();
                //when
                VegetableEntity saveVegetable = vegetableRepository.save(vegetableEntity);
                VegetableEntity nextSaveVegetable = vegetableRepository.save(nextVegetableEntity);

                // then
                assertThat(saveVegetable.getVegetableId()).isNotNull();
                assertThat(saveVegetable.getPrice()).isEqualTo(1000);
                assertThat(saveVegetable.getName()).isEqualTo("양파");

                assertThat(nextSaveVegetable.getVegetableId()).isNotNull();
                assertThat(nextSaveVegetable.getPrice()).isEqualTo(2000);
                assertThat(nextSaveVegetable.getName()).isEqualTo("파");
            }

        }
    }

    @Nested
    @DisplayName("실패케이스")
    class FailCase {
        @Nested
        @DisplayName("야채가게_저장_실패케이스")
        class SaveFailCase {
            @Test
            public void 야채_저장_name_Null(){
                // given
                VegetableEntity nextVegetableEntity = VegetableEntity.builder()
                        .price(2000)
                        .build();
                // when & then
                Assertions.assertThatThrownBy(()-> vegetableRepository.save(nextVegetableEntity)).isInstanceOf(DataIntegrityViolationException.class);
            }

            @Test
            public void 야채_저장_price_Null(){
                // given
                VegetableEntity nextVegetableEntity = VegetableEntity.builder()
                        .name("파")
                        .build();
                // when & then
                Assertions.assertThatThrownBy(()-> vegetableRepository.save(nextVegetableEntity)).isInstanceOf(DataIntegrityViolationException.class);
            }
        }
    }
}