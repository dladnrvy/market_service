package com.example.market_service.repository;

import com.example.market_service.domain.FruitEntity;
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

@ExtendWith(SpringExtension.class)
@Transactional
@DataJpaTest
class FruitRepositoryTest {

    @Autowired
    private FruitRepository fruitRepository;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private FruitEntity fruitEntity;

    private String FRUIT = "사과";
    private Integer PRICE = 1000;

    //    초기화
    @BeforeEach
    public void setUp() {
        fruitEntity = FruitEntity.builder()
                .name(FRUIT)
                .price(PRICE)
                .build();
    }

    @Nested
    @DisplayName("성공케이스")
    class SuccessCase{

        @Nested
        @DisplayName("과일가게_저장_성공케이스")
        class SaveSuccessCase{

            @Test
            public void 과일_저장(){
                // given
                FruitEntity nextFruitEntity = FruitEntity.builder()
                        .name("배")
                        .price(2000)
                        .build();
                //when
                FruitEntity saveFruit = fruitRepository.save(fruitEntity);
                FruitEntity nextSaveFruit = fruitRepository.save(nextFruitEntity);

                // then
                assertThat(saveFruit.getFruitId()).isNotNull();
                assertThat(saveFruit.getPrice()).isEqualTo(1000);
                assertThat(saveFruit.getName()).isEqualTo("사과");

                assertThat(nextSaveFruit.getFruitId()).isNotNull();
                assertThat(nextSaveFruit.getPrice()).isEqualTo(2000);
                assertThat(nextSaveFruit.getName()).isEqualTo("배");
            }

        }
    }

    @Nested
    @DisplayName("실패케이스")
    class FailCase {
        @Nested
        @DisplayName("과일가게_저장_실패케이스")
        class SaveFailCase {
            @Test
            public void 과일_저장_name_Null(){
                // given
                FruitEntity nextFruitEntity = FruitEntity.builder()
                        .price(2000)
                        .build();
                // when & then
                Assertions.assertThatThrownBy(()-> fruitRepository.save(nextFruitEntity)).isInstanceOf(DataIntegrityViolationException.class);
            }

            @Test
            public void 과일_저장_price_Null(){
                // given
                FruitEntity nextFruitEntity = FruitEntity.builder()
                        .name("배")
                        .build();
                // when & then
                Assertions.assertThatThrownBy(()-> fruitRepository.save(nextFruitEntity)).isInstanceOf(DataIntegrityViolationException.class);
            }
        }
    }
}