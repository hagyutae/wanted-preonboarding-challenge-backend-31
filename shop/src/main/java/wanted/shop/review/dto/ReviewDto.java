package wanted.shop.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDto {
    private Long id;
    private UserDto user;
    private Integer rating;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private boolean verifiedPurchase;
    private Integer helpfulVotes;

    @Getter
    @Builder
    public static class UserDto {
        private Long id;
        private String name;
        private String email;
        private String avatarUrl;
    }
}