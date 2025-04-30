package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductPriceDto(
        @NotNull @Min(0) @DecimalMax("9999999999.99")
        BigDecimal basePrice,
        @Min(0) @DecimalMax("9999999999.99")
        BigDecimal salePrice,
        @Min(0) @DecimalMax("9999999999.99")
        BigDecimal costPrice,
        @Size(max = 3)
        String currency,
        @Min(0) @DecimalMax("999.99")
        BigDecimal taxRate
) {
}
