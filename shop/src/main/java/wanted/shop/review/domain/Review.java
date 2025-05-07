package wanted.shop.review.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.shop.common.domain.DbDate;

@Getter
@Entity
@Table(name = "Reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @EmbeddedId
    private ReviewId reviewId;

    @Embedded
    private ProductId productId;

    @Embedded
    private UserId userId;

    @Embedded
    private ReviewData data;

    @Embedded
    private DbDate date;

    @Column(name = "verified_purchase")
    private String verifiedPurchase;

    @Column(name = "helpful_votes")
    private int helpfulVotes;

}
