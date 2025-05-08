package com.example.demo.review.dto;

import lombok.Builder;

@Builder
public record ReviewQueryFilter(
        Long productId,
        Integer rating
) {
}
