package com.wanted.ecommerce.category.dto.response;

import lombok.Builder;

@Builder
public record CategoryDetailResponse(
    Long id,
    String name,
    String slug,
    String description,
    Integer level,
    String imageUrl,
    ParentCategoryResponse parent
) {

    public static CategoryDetailResponse of(Long id, String name, String slug, String description,
        Integer level, String imageUrl, ParentCategoryResponse parent) {
        return CategoryDetailResponse.builder()
            .id(id)
            .name(name)
            .slug(slug)
            .description(description)
            .level(level)
            .imageUrl(imageUrl)
            .parent(parent)
            .build();
    }
}
