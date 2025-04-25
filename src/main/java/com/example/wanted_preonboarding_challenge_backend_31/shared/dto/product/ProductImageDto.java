package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductImageDto(
        @NotBlank @Size(max = 255)
        String url,
        @Size(max = 255)
        String altText,
        boolean isPrimary,
        int displayOrder,
        Long optionId
) {
}
