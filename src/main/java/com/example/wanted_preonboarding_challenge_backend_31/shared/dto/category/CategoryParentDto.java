package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.category.Category;

public record CategoryParentDto(
        Long id,
        String name,
        String slug
) {

    public static CategoryParentDto from(Category parentCategory) {
        if (parentCategory == null) {
            return null;
        }
        return new CategoryParentDto(parentCategory.getId(), parentCategory.getName(), parentCategory.getSlug());
    }
}
