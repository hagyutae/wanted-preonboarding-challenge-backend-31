package com.sandro.wanted_shop.category.web;

import com.sandro.wanted_shop.config.IntegrationTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest extends IntegrationTestContext {
    @DisplayName("모든 카테고리를 계층 구조로 조회한다.")
    @Test
    void getAllCategories() throws Exception {
        ResultActions resultActions = mvc.perform(get("/api/categories"));

        resultActions
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.size()").value(8),
                        jsonPath("$[*].id").exists()
                )
                .andDo(print());
    }

    @DisplayName("특정 카테고리의 상품 목록을 조회한다.")
    @Test
    void getProductsByCategory() throws Exception {
        ResultActions resultActions = mvc.perform(get("/api/categories/{id}/products", 10L));

        resultActions
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.size()").value(2)
                )
                .andDo(print());
    }

}