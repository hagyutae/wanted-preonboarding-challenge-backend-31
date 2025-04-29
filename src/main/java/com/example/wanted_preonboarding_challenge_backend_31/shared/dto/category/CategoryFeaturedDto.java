package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category;

public record CategoryFeaturedDto(
        Long id,
        String name,
        String slug,
        String imageUrl,
        Long productCount
) {
}
