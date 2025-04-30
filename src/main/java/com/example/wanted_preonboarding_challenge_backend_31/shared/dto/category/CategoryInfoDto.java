package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.category.Category;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductCategory;

public record CategoryInfoDto(
        Long id,
        String name,
        String slug,
        boolean isPrimary,
        CategoryParentDto parent
) {

    public static CategoryInfoDto from(ProductCategory productCategory) {
        Category category = productCategory.getCategory();
        return new CategoryInfoDto(
                category.getId(),
                category.getName(),
                category.getSlug(),
                productCategory.isPrimary(),
                CategoryParentDto.from(category.getParentCategory())
        );
    }
}
