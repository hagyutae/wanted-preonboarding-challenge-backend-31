package wanted.domain.review.dto.response;

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
}
