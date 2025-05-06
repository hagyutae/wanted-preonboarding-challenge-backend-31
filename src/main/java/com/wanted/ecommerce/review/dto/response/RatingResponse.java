package com.wanted.ecommerce.review.dto.response;

import java.util.Map;
import lombok.Builder;

@Builder
public record RatingResponse(
    Double average,
    Integer count,
    Map<Integer, Long> distribution
) {
    public static RatingResponse of(Double average, Integer count, Map<Integer, Long> distribution){
        return RatingResponse.builder()
            .average(average)
            .count(count)
            .distribution(distribution)
            .build();
    }
}
