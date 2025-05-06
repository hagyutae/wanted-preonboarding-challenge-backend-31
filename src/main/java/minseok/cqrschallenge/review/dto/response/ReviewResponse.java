package minseok.cqrschallenge.review.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private UserResponse user;
    private Integer rating;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean verifiedPurchase;
    private Integer helpfulVotes;
    
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponse {
        private Long id;
        private String name;
        private String avatarUrl;
    }
}