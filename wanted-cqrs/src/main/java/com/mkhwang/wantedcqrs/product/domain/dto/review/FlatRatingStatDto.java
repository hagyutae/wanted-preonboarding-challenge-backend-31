package com.mkhwang.wantedcqrs.product.domain.dto.review;

public record FlatRatingStatDto(
        Double average,
        Integer count,
        Integer rating1,
        Integer rating2,
        Integer rating3,
        Integer rating4,
        Integer rating5
) {
}
