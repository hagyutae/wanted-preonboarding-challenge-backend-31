package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product;

import java.math.BigDecimal;
import java.util.Map;

public record ProductDetailDto(
        BigDecimal weight,
        Map<String, Object> dimensions,
        String materials,
        String countryOfOrigin,
        String warrantyInfo,
        String careInstructions,
        Map<String, Object> additionalInfo
) {
}
