package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Map;

public record ProductDetailDto(
        @Min(0) @DecimalMax("99999999.99")
        BigDecimal weight,
        Map<String, Object> dimensions,
        String materials,
        @Size(max = 100)
        String countryOfOrigin,
        String warrantyInfo,
        String careInstructions,
        Map<String, Object> additionalInfo
) {
}
