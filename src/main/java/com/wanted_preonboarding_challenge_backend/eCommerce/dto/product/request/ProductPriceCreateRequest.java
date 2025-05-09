package com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProductPriceCreateRequest {
    private Integer basePrice;
    private Integer salePrice;
    private Integer costPrice;
    private String currency;
    private Integer taxRate;
}
