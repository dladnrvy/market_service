package com.example.market_service.controller;

import com.example.market_service.config.JwtProvider;
import com.example.market_service.domain.FruitEntity;
import com.example.market_service.interceptor.NotAuth;
import com.example.market_service.repository.FruitRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FruitControllerTest {

    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private FruitSetUp setUp;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtProvider jwtProvider;


    @Nested
    @DisplayName("성공케이스")
    class SuccessCase{
        @Test
        public void 과일가게_토큰_생성() throws Exception{
            // given
            // when
            ResultActions resultActions = mockMvc.perform(get("/fruit/token"))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("code").value("200"));
        }

        @Test
        public void 모든_과일이름_조회() throws Exception {
            // given
            given(jwtProvider.getJwt()).willReturn("");
            given(jwtProvider.parseJwtToken(anyString(),anyString())).willReturn(true);
            // when
            ResultActions resultActions = mockMvc.perform(get("/fruit/product")
                            .header("Authorization", "Bearer (accessToken)"))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk());
        }

        @Test
        public void 과일_이름_가격_조회() throws Exception {
            // given
            given(jwtProvider.getJwt()).willReturn("");
            given(jwtProvider.parseJwtToken(anyString(),anyString())).willReturn(true);

            FruitEntity fruitEntity = FruitEntity.builder()
                    .name("테스트과일")
                    .price(1000)
                    .build();
            setUp.saveFruit(fruitEntity);

            // when
            ResultActions resultActions = mockMvc.perform(get("/fruit/product")
                    .param("name","테스트과일")
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
        public void 모든_과일이름_조회_TOKEN_NULL() throws Exception {
            // when
            ResultActions resultActions = mockMvc.perform(get("/fruit/product"))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isBadRequest());
        }

        @Test
        public void 과일_이름_가격_조회_TOKEN_NULL() throws Exception {
            // given
            FruitEntity fruitEntity = FruitEntity.builder()
                    .name("테스트과일")
                    .price(1000)
                    .build();
            setUp.saveFruit(fruitEntity);
            // when
            ResultActions resultActions = mockMvc.perform(get("/fruit/product")
                            .param("name","테스트과일"))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isBadRequest());
        }

        @Test
        public void 과일_이름_가격_조회_NAME_NULL() throws Exception {
            // given
            given(jwtProvider.getJwt()).willReturn("");
            given(jwtProvider.parseJwtToken(anyString(),anyString())).willReturn(true);
            // when
            ResultActions resultActions = mockMvc.perform(get("/fruit/product")
                            .param("name","없는과일")
                            .header("Authorization", "Bearer (accessToken)"))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isBadRequest());
        }
    }
}


@Component
public class FruitSetUp {
    @Autowired
    private FruitRepository fruitRepository;

    public void saveFruit(FruitEntity fruitEntity) {
        fruitRepository.save(fruitEntity);
    }
}