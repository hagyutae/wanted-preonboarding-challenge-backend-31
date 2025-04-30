package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.brand;

public record BrandDetailDto(
        Long id,
        String name,
        String description,
        String logoUrl,
        String website
) {
}
