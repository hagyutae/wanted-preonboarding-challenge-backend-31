package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductOptionDto(
        @NotBlank @Size(max = 100)
        String name,
        @Min(0) @DecimalMax("9999999999.99")
        BigDecimal additionalPrice,
        @Size(max = 100)
        String sku,
        int stock,
        int displayOrder
) {
}
