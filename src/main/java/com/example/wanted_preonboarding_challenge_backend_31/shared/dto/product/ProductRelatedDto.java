package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product;

import java.math.BigDecimal;

public record ProductRelatedDto(
        Long id,
        String name,
        String slug,
        String shortDescription,
        ProductImageSearchDto primaryImage,
        BigDecimal basePrice,
        BigDecimal salePrice,
        String currency
) {
}
