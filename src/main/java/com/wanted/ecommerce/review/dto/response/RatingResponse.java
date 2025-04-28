package com.wanted.ecommerce.review.dto.response;

import java.util.Map;
import lombok.Builder;

@Builder
public record RatingResponse(
    Double average,
    Integer count,
    Map<String, Integer> distribution
) {
    public static RatingResponse of(Double average, Integer count, Map<String, Integer> distribution){
        return RatingResponse.builder()
            .average(average)
            .count(count)
            .distribution(distribution)
            .build();
    }
}
