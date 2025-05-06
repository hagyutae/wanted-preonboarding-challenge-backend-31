package com.sandro.wanted_shop.category;

import java.util.List;

public record CategoryDto(
        Long id,
        String name,
        String slug,
        String description,
        Integer level,
        String imageUrl,
        List<CategoryDto> children
) {
    public static CategoryDto from(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getDescription(),
                category.getLevel(),
                category.getImageUrl(),
                category.getChildren().stream()
                        .map(CategoryDto::from)
                        .toList()
        );
    }
}
