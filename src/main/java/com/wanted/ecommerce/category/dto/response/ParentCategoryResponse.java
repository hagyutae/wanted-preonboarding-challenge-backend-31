package com.wanted.ecommerce.category.dto.response;

import lombok.Builder;

@Builder
public record ParentCategoryResponse(
    Long id,
    String name,
    String slug
) {
    public static ParentCategoryResponse of(Long id, String name, String slug){
        return ParentCategoryResponse.builder()
            .id(id)
            .name(name)
            .slug(slug)
            .build();
    }
}
