package com.sandro.wanted_shop.product.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductOptionTest {

    @DisplayName("생성 시 이름이 null 또는 빈 문자라면 예외가 발생한다.")
    @Test
    void checkRequiredValues() throws Exception {
        assertThatThrownBy(() -> ProductOption.builder().name(null).build());
        assertThatThrownBy(() -> ProductOption.builder().name("").build());
    }
}