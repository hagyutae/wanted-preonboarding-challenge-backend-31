package com.preonboarding.challenge.service.mapper;

import com.preonboarding.challenge.service.entity.Review;
import com.preonboarding.challenge.service.entity.User;
import com.preonboarding.challenge.service.dto.ReviewDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReviewMapper {

    public ReviewDto.Review toReviewDto(Review review) {
        return ReviewDto.Review.builder()
                .id(review.getId())
                .user(toUserDto(review.getUser()))
                .rating(review.getRating())
                .title(review.getTitle())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .verifiedPurchase(review.isVerifiedPurchase())
                .helpfulVotes(review.getHelpfulVotes())
                .build();
    }

    public ReviewDto.User toUserDto(User user) {
        return ReviewDto.User.builder()
                .id(user.getId())
                .name(user.getName())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }

    public ReviewDto.ReviewSummary toReviewSummary(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return ReviewDto.ReviewSummary.builder()
                    .averageRating(0.0)
                    .totalCount(0)
                    .distribution(Map.of())
                    .build();
        }

        // 평균 평점 계산
        double averageRating = reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        // 평점별 분포 계산
        Map<Integer, Integer> distribution = reviews.stream()
                .collect(Collectors.groupingBy(
                        Review::getRating,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));

        // 1-5 평점 모두 포함하도록 보정
        for (int i = 1; i <= 5; i++) {
            distribution.putIfAbsent(i, 0);
        }

        return ReviewDto.ReviewSummary.builder()
                .averageRating(averageRating)
                .totalCount(reviews.size())
                .distribution(distribution)
                .build();
    }
}
