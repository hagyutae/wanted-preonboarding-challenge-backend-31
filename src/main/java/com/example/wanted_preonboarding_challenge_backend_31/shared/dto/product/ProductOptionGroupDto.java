package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record ProductOptionGroupDto(
        @NotBlank @Size(max = 100)
        String name,
        int displayOrder,
        List<ProductOptionDto> options
) {
}
