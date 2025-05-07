package com.sandro.wanted_shop.mainpage;

import com.sandro.wanted_shop.config.IntegrationTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MainPageControllerTest extends IntegrationTestContext {

    @DisplayName("메인 페이지 데이터 조회")
    @Test
    void mainPage() throws Exception {
        ResultActions resultActions = mvc.perform(get("/api/main"));

        resultActions
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.product", nullValue()),
                        jsonPath("$.newProduct.size()").value(10),
                        jsonPath("$.categories").exists()
                )
                .andDo(print());
    }

}