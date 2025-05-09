package wanted.shop.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDto {
    private Long id;
    private UserDto user;
    private int rating;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private boolean verifiedPurchase;
    private int helpfulVotes;

    @Getter
    @Builder
    public static class UserDto {
        private long id;
        private String name;
        private String email;
        private String avatarUrl;
    }
}