package com.wanted.ecommerce.tag.dto.response;

import lombok.Builder;

@Builder
public record TagResponse(
    Long id,
    String name,
    String slug
) {
    public static TagResponse of(Long id, String name, String slug){
        return TagResponse.builder()
            .id(id)
            .name(name)
            .slug(slug)
            .build();
    }
}
