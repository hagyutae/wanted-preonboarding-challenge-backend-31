package com.example.demo.review.dto;

import com.querydsl.core.annotations.QueryProjection;

public record ReviewStatistic(
        Long productId,
        Long reviewCount,
        Double averageRating
) {
    @QueryProjection
    public ReviewStatistic {

    }
}
