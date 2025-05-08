package com.example.demo.product.dto;

import com.querydsl.core.annotations.QueryProjection;

public record FeaturedCategory(
        Long id,
        String name,
        String slug,
        String imageUrl,
        Long productCount
) {
    @QueryProjection
    public FeaturedCategory {}
}
