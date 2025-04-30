package com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response;

import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.user.UserInfoDto;
import java.time.LocalDateTime;

public record ProductReviewCreateRes(
        Long id,
        UserInfoDto user,
        int rating,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean verifiedPurchase,
        int helpfulVotes
) {
}
