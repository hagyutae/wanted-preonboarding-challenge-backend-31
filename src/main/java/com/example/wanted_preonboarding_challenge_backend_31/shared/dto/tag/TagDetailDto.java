package com.example.wanted_preonboarding_challenge_backend_31.shared.dto.tag;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.tag.Tag;

public record TagDetailDto(
        Long id,
        String name,
        String slug
) {

    public static TagDetailDto from(Tag tag) {
        return new TagDetailDto(
                tag.getId(),
                tag.getName(),
                tag.getSlug()
        );
    }
}
