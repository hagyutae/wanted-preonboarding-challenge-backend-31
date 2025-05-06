package minseok.cqrschallenge.review.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import minseok.cqrschallenge.common.dto.PaginationResponse;
import minseok.cqrschallenge.review.dto.response.ReviewListResponse;
import minseok.cqrschallenge.review.dto.response.ReviewResponse;
import minseok.cqrschallenge.review.dto.response.ReviewUpdateResponse;
import minseok.cqrschallenge.review.entity.Review;
import minseok.cqrschallenge.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {


    public ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
            .id(review.getId())
            .user(buildUserResponse(review.getUser()))
            .rating(review.getRating())
            .title(review.getTitle())
            .content(review.getContent())
            .createdAt(review.getCreatedAt())
            .updatedAt(review.getUpdatedAt())
            .verifiedPurchase(review.getVerifiedPurchase())
            .helpfulVotes(review.getHelpfulVotes())
            .build();
    }


    public ReviewListResponse toListResponse(Page<Review> reviewPage, int page, int perPage,
        Long productId) {
        List<ReviewResponse> reviewResponses = reviewPage.getContent().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());

        PaginationResponse.Pagination pagination = PaginationResponse.Pagination.builder()
            .totalItems(reviewPage.getTotalElements())
            .totalPages(reviewPage.getTotalPages())
            .currentPage(page)
            .perPage(perPage)
            .build();

        ReviewListResponse.ReviewSummary summary = calculateReviewSummary(reviewPage, productId);

        return ReviewListResponse.builder()
            .items(reviewResponses)
            .summary(summary)
            .pagination(pagination)
            .build();
    }

    public ReviewUpdateResponse toUpdateResponse(Review review) {
        return ReviewUpdateResponse.builder()
            .id(review.getId())
            .rating(review.getRating())
            .title(review.getTitle())
            .content(review.getContent())
            .updatedAt(review.getUpdatedAt())
            .build();
    }


    public ReviewListResponse.ReviewSummary calculateReviewSummary(
        Page<Review> reviewPage, Long productId, Double averageRating, Integer totalCount,
        List<Object[]> ratingDistribution) {

        Map<Integer, Integer> distribution = new HashMap<>();

        for (int i = 1; i <= 5; i++) {
            distribution.put(i, 0);
        }

        for (Object[] result : ratingDistribution) {
            Integer rating = ((Number) result[0]).intValue();
            Integer count = ((Number) result[1]).intValue();
            distribution.put(rating, count);
        }

        return ReviewListResponse.ReviewSummary.builder()
            .averageRating(averageRating)
            .totalCount(totalCount)
            .distribution(distribution)
            .build();
    }


    private ReviewListResponse.ReviewSummary calculateReviewSummary(
        Page<Review> reviewPage, Long productId) {

        List<Review> reviews = reviewPage.getContent();

        Double averageRating = reviews.stream()
            .mapToInt(Review::getRating)
            .average()
            .orElse(0.0);

        Integer totalCount = (int) reviewPage.getTotalElements();

        Map<Integer, Integer> distribution = new HashMap<>();

        for (int i = 1; i <= 5; i++) {
            distribution.put(i, 0);
        }

        for (Review review : reviews) {
            int rating = review.getRating();
            distribution.put(rating, distribution.get(rating) + 1);
        }

        return ReviewListResponse.ReviewSummary.builder()
            .averageRating(averageRating)
            .totalCount(totalCount)
            .distribution(distribution)
            .build();
    }


    private ReviewResponse.UserResponse buildUserResponse(User user) {
        if (user == null) {
            return null;
        }

        return ReviewResponse.UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .avatarUrl(user.getAvatarUrl())
            .build();
    }
}