package com.wanted_preonboarding_challenge_backend.eCommerce.dto.price;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PriceDto {
    private Integer basePrice;
    private Integer salePrice;
    private String currency;
    private Integer taxRate;
    private Integer discountPercentage;
}
