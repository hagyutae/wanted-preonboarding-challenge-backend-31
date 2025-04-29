package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.category.Category;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductCategory;
import java.util.Optional;

public record CategoryDetailDto(
        Long id,
        String name,
        String slug,
        boolean isPrimary,
        CategoryParentDto parent
) {

    public static CategoryDetailDto from(ProductCategory productCategory) {
        Category category = productCategory.getCategory();
        CategoryParentDto parentDto = Optional.ofNullable(category.getParentCategory())
                .map(parent -> new CategoryParentDto(
                        parent.getId(),
                        parent.getName(),
                        parent.getSlug())
                )
                .orElse(null);
        return new CategoryDetailDto(
                category.getId(),
                category.getName(),
                category.getSlug(),
                productCategory.isPrimary(),
                parentDto
        );
    }
}
