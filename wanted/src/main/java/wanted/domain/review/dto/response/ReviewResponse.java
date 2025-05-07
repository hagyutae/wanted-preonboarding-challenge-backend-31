package wanted.domain.review.dto.response;

import wanted.domain.product.dto.response.ProductCreateResponse;
import wanted.domain.product.entity.Product;
import wanted.domain.review.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        ReviewUserResponse user,
        int rating,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean verifiedPurchase,
        int helpfulVotes
) {
    public static ReviewResponse of(Review review) {
        return new ReviewResponse(
                review.getId(),
                ReviewUserResponse.of(review.getUser()),
                review.getRating(),
                review.getTitle(),
                review.getContent(),
                review.getCreatedAt(),
                review.getUpdatedAt(),
                review.isVerifiedPurchase(),
                review.getHelpfulVotes()
        );
    }
}
