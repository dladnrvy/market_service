package com.example.market_service.controller;

import com.example.market_service.config.JwtProvider;
import com.example.market_service.domain.FruitEntity;
import com.example.market_service.domain.VegetableEntity;
import com.example.market_service.repository.FruitRepository;
import com.example.market_service.repository.VegetableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class VegetableControllerTest {

    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private VegetableSetUp setUp;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtProvider jwtProvider;

    @Nested
    @DisplayName("성공케이스")
    class SuccessCase{
        @Test
        public void 야채가게_토큰_생성() throws Exception{
            // given
            // when
            ResultActions resultActions = mockMvc.perform(get("/vegetable/token"))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("code").value("200"));
        }

        @Test
        public void 모든_야채이름_조회() throws Exception {
            // given
            given(jwtProvider.parseJwtToken(anyString(),anyString())).willReturn(true);
            // when
            ResultActions resultActions = mockMvc.perform(get("/vegetable/product")
                            .header("Authorization", "Bearer (accessToken)"))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk());
        }

        @Test
        public void 야채_이름_가격_조회() throws Exception {
            // given
            given(jwtProvider.parseJwtToken(anyString(),anyString())).willReturn(true);

            VegetableEntity vegetableEntity = VegetableEntity.builder()
                    .name("테스트야채")
                    .price(1000)
                    .build();
            setUp.saveVegetable(vegetableEntity);
            // when
            ResultActions resultActions = mockMvc.perform(get("/vegetable/product")
                            .param("name","테스트야채")
                            .header("Authorization", "Bearer (accessToken)"))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("code").value("200"));
        }

    }

    @Nested
    @DisplayName("실패케이스")
    class FailCase {

        @Test
        public void 모든_야채이름_조회_TOKEN_NULL() throws Exception {
            // when
            ResultActions resultActions = mockMvc.perform(get("/vegetable/product"))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isBadRequest());
        }

        @Test
        public void 야채_이름_가격_조회_TOKEN_NULL() throws Exception {
            // given
            VegetableEntity vegetableEntity = VegetableEntity.builder()
                    .name("테스트야채")
                    .price(1000)
                    .build();
            setUp.saveVegetable(vegetableEntity);
            // when
            ResultActions resultActions = mockMvc.perform(get("/vegetable/product")
                            .param("name","테스트야채"))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isBadRequest());
        }

        @Test
        public void 야채_이름_가격_조회_NAME_NULL() throws Exception {
            // given
            given(jwtProvider.parseJwtToken(anyString(),anyString())).willReturn(true);
            // when
            ResultActions resultActions = mockMvc.perform(get("/vegetable/product")
                            .param("name","없는야채")
                            .header("Authorization", "Bearer (accessToken)"))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isBadRequest());
        }
    }
}

@Component
public class VegetableSetUp {
    @Autowired
    private VegetableRepository vegetableRepository;

    public void saveVegetable(VegetableEntity vegetableEntity) {
        vegetableRepository.save(vegetableEntity);
    }
}