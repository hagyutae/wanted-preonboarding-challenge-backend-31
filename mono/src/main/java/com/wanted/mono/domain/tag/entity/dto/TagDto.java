package com.wanted.mono.domain.tag.entity.dto;

import com.wanted.mono.domain.tag.entity.ProductTag;
import com.wanted.mono.domain.tag.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private Long id;
    private String name;
    private String slug;

    // ----------
    public static TagDto of(Tag tag) {
        TagDto dto = new TagDto();
        dto.id = tag.getId();
        dto.name = tag.getName();
        dto.slug = tag.getSlug();
        return dto;
    }
}
