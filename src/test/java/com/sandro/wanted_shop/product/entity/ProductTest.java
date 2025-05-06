package com.sandro.wanted_shop.product.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @DisplayName("상품 생성 시 필수 값이 누락된 경우 예외가 발생한다.")
    @Test
    void validate() throws Exception {
        assertThatThrownBy(() -> Product.builder().build())
                .isInstanceOf(AssertionError.class)
                .hasMessage("Product name, slug, status must be present");
    }

}