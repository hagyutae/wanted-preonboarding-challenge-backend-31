package com.example.preonboarding.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewDTO {
    private Long productsId;
    private Double rating;
    private Long reviewCount;

    @QueryProjection
    @Builder
    public ReviewDTO(Long productsId, Double rating, Long reviewCount) {
        this.productsId = productsId;
        this.rating = roundToTwoDecimalPlaces(rating) ;
        this.reviewCount = reviewCount;
    }

    private double roundToTwoDecimalPlaces(Double value) {
        if (value == null) return 0.0;
        return Math.round(value * 100.0) / 100.0;
    }
}
