package com.psh10066.commerce.api.dto.request;

public record CreateReviewRequest(
    Integer rating,
    String title,
    String content
) {
}
