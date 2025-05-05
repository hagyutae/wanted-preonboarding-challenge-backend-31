package com.example.cqrsapp.main.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class CategoryDto {

    private final Long id;
    private final String name;
    private final String slug;
    private final String imageUrl;
    private final Long productCount;

    @QueryProjection
    public CategoryDto(Long id, String name, String slug, String imageUrl, Long productCount) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.imageUrl = imageUrl;
        this.productCount = productCount;
    }
}
