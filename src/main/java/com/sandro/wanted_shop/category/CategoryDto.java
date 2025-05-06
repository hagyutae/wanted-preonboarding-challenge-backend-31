package com.sandro.wanted_shop.category;

import com.sandro.wanted_shop.product.entity.relation.ProductCategory;

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

    public static List<CategoryDto> from(List<ProductCategory> categories) {
        return categories.stream()
                .map(productCategory -> {
                    Category category = productCategory.getCategory();
                    return CategoryDto.from(category);
                }).toList();
    }
}
