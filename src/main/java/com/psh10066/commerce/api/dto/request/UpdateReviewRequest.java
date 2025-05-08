package com.psh10066.commerce.api.dto.request;

public record UpdateReviewRequest(
    Integer rating,
    String title,
    String content
) {
}
