package wanted.domain.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.common.entity.BaseCreateUpdateEntity;
import wanted.domain.product.entity.Product;
import wanted.domain.review.dto.request.ProductReviewRequest;
import wanted.domain.user.entity.User;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseCreateUpdateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Min(1)
    @Max(5)
    private int rating;

    private String title;

    private String content;

    private boolean verifiedPurchase;

    private int helpfulVotes;

    @Builder
    public Review(Product product, User user, int rating, String title, String content, boolean verifiedPurchase, int helpfulVotes) {
        this.product = product;
        this.user = user;
        this.rating = rating;
        this.title = title;
        this.content = content;
        this.verifiedPurchase = verifiedPurchase;
        this.helpfulVotes = helpfulVotes;
    }

    public static Review from(ProductReviewRequest request, User user, Product product) {
        return Review.builder()
                .product(product)
                .user(user)
                .rating(request.rating())
                .title(request.title())
                .content(request.content())
                .helpfulVotes(0)
                .verifiedPurchase(true)
                .build();
    }

    public void update(ProductReviewRequest request) {
        this.rating = request.rating();
        this.title = request.title();
        this.content = request.content();
    }
}
