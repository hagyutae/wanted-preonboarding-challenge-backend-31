package com.wanted.ecommerce.category.dto.response;

import lombok.Builder;

@Builder
public record CategoryResponse(
    Long id,
    String name,
    String slug,
    Boolean isPrimary,
    ParentCategoryResponse parent

) {

    public static CategoryResponse of(Long id, String name, String slug, Boolean isPrimary,
        ParentCategoryResponse parent) {
        return CategoryResponse.builder()
            .id(id)
            .name(name)
            .slug(slug)
            .isPrimary(isPrimary)
            .parent(parent)
            .build();
    }
}
