package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product;

import java.math.BigDecimal;

public record ProductPriceDto(
        BigDecimal basePrice,
        BigDecimal salePrice,
        BigDecimal costPrice,
        String currency,
        BigDecimal taxRate
) {
}
