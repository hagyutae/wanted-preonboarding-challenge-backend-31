package com.shopping.mall.review.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ReviewSummaryResponse {

    private Double averageRating;
    private Long totalCount;
    private Map<Integer, Long> distribution;
}
