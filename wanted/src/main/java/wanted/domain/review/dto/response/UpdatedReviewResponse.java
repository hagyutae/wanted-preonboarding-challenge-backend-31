package wanted.domain.review.dto.response;

import wanted.domain.review.entity.Review;

import java.time.LocalDateTime;

public record UpdatedReviewResponse(
        Long id,
        int rating,
        String title,
        String content,
        LocalDateTime updatedAt
) {
    public static UpdatedReviewResponse from(Review review) {
        return new UpdatedReviewResponse(
                review.getId(),
                review.getRating(),
                review.getTitle(),
                review.getContent(),
                review.getUpdatedAt()
        );
    }
}

