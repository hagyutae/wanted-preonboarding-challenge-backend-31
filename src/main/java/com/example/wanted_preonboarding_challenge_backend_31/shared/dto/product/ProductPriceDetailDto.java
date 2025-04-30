package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product;

import java.math.BigDecimal;

public record ProductPriceDetailDto(
        BigDecimal basePrice,
        BigDecimal salePrice,
        String currency,
        BigDecimal taxRate,
        Integer discountPercentage
) {

    public static ProductPriceDetailDto from(ProductPriceDetailDto origin, Integer discountPercentage) {
        return new ProductPriceDetailDto(origin.basePrice, origin.salePrice, origin.currency, origin.taxRate,
                discountPercentage);
    }
}
