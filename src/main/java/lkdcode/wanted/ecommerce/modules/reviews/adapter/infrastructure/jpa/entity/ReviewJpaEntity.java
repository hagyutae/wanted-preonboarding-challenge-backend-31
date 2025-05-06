package lkdcode.wanted.ecommerce.modules.reviews.adapter.infrastructure.jpa.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lkdcode.wanted.ecommerce.framework.common.jpa.entity.EntityValidator;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductJpaEntity;
import lkdcode.wanted.ecommerce.modules.users.adapter.infrastructure.jpa.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewJpaEntity extends EntityValidator<ReviewJpaEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductJpaEntity product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Min(1)
    @Max(5)
    @NotNull
    @Column(nullable = false)
    private Integer rating;

    @Size(max = 255)
    private String title;

    @Column(columnDefinition = "text")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "verified_purchase")
    private Boolean verifiedPurchase = false;

    @Column(name = "helpful_votes")
    private Integer helpfulVotes = 0;

    @Builder
    public ReviewJpaEntity(Long id, ProductJpaEntity product, User user, Integer rating, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean verifiedPurchase, Integer helpfulVotes) {
        this.id = id;
        this.product = product;
        this.user = user;
        this.rating = rating;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.verifiedPurchase = verifiedPurchase;
        this.helpfulVotes = helpfulVotes;
        super.validSelf();
    }
}
