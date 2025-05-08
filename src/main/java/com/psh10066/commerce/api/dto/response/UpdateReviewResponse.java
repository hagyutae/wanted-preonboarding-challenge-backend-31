package com.psh10066.commerce.api.dto.response;

import java.time.LocalDateTime;

public record UpdateReviewResponse(
    Long id,
    Integer rating,
    String title,
    String content,
    LocalDateTime updatedAt
) {
}
