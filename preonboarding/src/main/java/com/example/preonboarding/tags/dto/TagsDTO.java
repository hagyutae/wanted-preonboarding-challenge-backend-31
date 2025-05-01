package com.example.preonboarding.tags.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class TagsDTO {
    private Long id;
    private String name;
    private String slug;

    public TagsDTO(Long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }
}
