package com.example.wanted_preonboarding_challenge_backend_31.web.review.dto.response;

import java.time.LocalDateTime;

public record ReviewUpdateRes(
        Long id,
        int rating,
        String title,
        String content,
        LocalDateTime updatedAt
) {
}
