package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category;

public record CategoryDetailDto(
        Long id,
        String name,
        String slug,
        boolean isPrimary,
        CategoryParentDto parent
) {
}
