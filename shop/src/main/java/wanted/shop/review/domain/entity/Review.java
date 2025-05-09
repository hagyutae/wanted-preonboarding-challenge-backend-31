package wanted.shop.review.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.shop.product.domain.ProductId;
import wanted.shop.review.dto.ReviewDto;
import wanted.shop.review.dto.ReviewSummaryDto;
import wanted.shop.user.domain.User;

@Getter
@Entity
@Table(name = "Reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @EmbeddedId
    private ReviewId reviewId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "product_id"))
    private ProductId productId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "rating", column = @Column(name = "rating")),
            @AttributeOverride(name = "title", column = @Column(name = "title")),
            @AttributeOverride(name = "content", column = @Column(name = "content"))
    })
    private ReviewData reviewData;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "createdAt", column = @Column(name = "created_at")),
            @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at")),
            @AttributeOverride(name = "deletedAt", column = @Column(name = "deleted_at")),
    })
    private ReviewTimestamps timestamps;

    @Column(name = "verified_purchase")
    private String verifiedPurchase;

    @Column(name = "helpful_votes")
    private int helpfulVotes;


    public ReviewDto toReviewDto() {
        return ReviewDto.builder()
                .id(reviewId.getId())
                .user(ReviewDto.UserDto.builder()
                        .id(user.getUserid().getId())
                        .name(user.getUserInfo().getName())
                        .email(user.getUserInfo().getEmail())
                        .avatarUrl(user.getUserInfo().getAvatarUrl())
                        .build())
                .rating(reviewData.getRating())
                .title(reviewData.getTitle())
                .content(reviewData.getContent())
                .createdAt(timestamps.getCreatedAt().toString())
                .updatedAt(timestamps.getUpdatedAt().toString())
                .verifiedPurchase("Y".equalsIgnoreCase(verifiedPurchase))
                .helpfulVotes(helpfulVotes)
                .build();
    }

}
