package com.wanted_preonboarding_challenge_backend.eCommerce.dto.option.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductOptionResponse {
    private Long id;
    private String name;
    private Integer additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;
}