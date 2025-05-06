package com.wanted.ecommerce.tag.dto.response;

import com.wanted.ecommerce.tag.domain.Tag;
import lombok.Builder;

@Builder
public record TagResponse(
    Long id,
    String name,
    String slug
) {
    public static TagResponse of(Tag tag){
        return TagResponse.builder()
            .id(tag.getId())
            .name(tag.getName())
            .slug(tag.getSlug())
            .build();
    }
}
