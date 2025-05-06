package com.sandro.wanted_shop.review.dto;

import com.sandro.wanted_shop.review.entity.Review;

import java.util.List;

public record ReviewDto(
        Long id,
        UserDto user,
        int rating,
        String title,
        String content,
        boolean verifiedPurchase,
        int helpfulVotes
) {
    public static ReviewDto from(Review review) {
        return new ReviewDto(
                review.getId(),
                UserDto.from(review.getUser()),
                review.getRating(),
                review.getTitle(),
                review.getContent(),
                review.getVerifiedPurchase(),
                review.getHelpfulVotes()
        );
    }

    public static List<ReviewDto> from(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewDto::from)
                .toList();
    }
}
