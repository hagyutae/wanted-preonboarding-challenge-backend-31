package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.category.Category;

public record CategoryDetailDto(
        Long id,
        String name,
        String slug,
        String description,
        int level,
        String imageUrl,
        CategoryParentDto parent
) {

    public static CategoryDetailDto from(Category category) {
        return new CategoryDetailDto(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getDescription(),
                category.getLevel(),
                category.getImageUrl(),
                CategoryParentDto.from(category.getParentCategory())
        );
    }
}
