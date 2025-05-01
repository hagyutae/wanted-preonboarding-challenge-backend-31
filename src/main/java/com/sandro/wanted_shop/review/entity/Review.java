package com.sandro.wanted_shop.review.entity;

import com.sandro.wanted_shop.common.entity.BaseTimeEntity;
import com.sandro.wanted_shop.product.entity.Product;
import com.sandro.wanted_shop.review.UpdateReviewCommand;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private Integer rating; // 1 ~ 5
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Boolean verifiedPurchase;
    private Integer helpfulVotes;

    @Builder
    public Review(Product product, User user, Integer rating, String title, String content,
                  Boolean verifiedPurchase, Integer helpfulVotes) {
        this.product = product;
        this.user = user;
        this.rating = rating;
        this.title = title;
        this.content = content;
        this.verifiedPurchase = Optional.ofNullable(verifiedPurchase).orElse(false);
        this.helpfulVotes = Optional.ofNullable(helpfulVotes).orElse(0);

        validate();

        this.user.addReview(this);
    }

    private void validate() {
        assert product != null
                && user != null
                && rating != null
                : "product, user and rating are required";

        assert this.rating >= 1 && this.rating <= 5 : "rating must be between 1 and 5";
    }

    public void update(UpdateReviewCommand command) {
        this.rating = command.rating();
        this.title = command.title();
        this.content = command.content();
        this.verifiedPurchase = command.verifiedPurchase();
        this.helpfulVotes = command.helpfulVotes();
    }
}