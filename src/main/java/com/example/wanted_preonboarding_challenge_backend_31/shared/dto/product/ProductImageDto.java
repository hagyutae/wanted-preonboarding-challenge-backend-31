package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product;

public record ProductImageDto(
        String url,
        String altText,
        boolean isPrimary,
        int displayOrder,
        Long optionId
) {
}
