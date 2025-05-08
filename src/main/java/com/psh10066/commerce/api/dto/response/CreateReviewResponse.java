package com.psh10066.commerce.api.dto.response;

import java.time.LocalDateTime;

public record CreateReviewResponse(
    Long id,
    UserDto user,
    Integer rating,
    String title,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Boolean verifiedPurchase,
    Integer helpfulVotes
) {

    public record UserDto(
        Long id,
        String name,
        String avatarUrl
    ) {
    }
}
