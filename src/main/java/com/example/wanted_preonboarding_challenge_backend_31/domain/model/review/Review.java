package com.example.wanted_preonboarding_challenge_backend_31.domain.model.review;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.basic.DateEntity;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.Product;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.user.User;
import jakarta.persistence.Column;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Review extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Min(1)
    @Max(5)
    private int rating;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean verifiedPurchase;

    private int helpfulVotes;


    public static Review create(Product product, User user, int rating, String title, String content) {
        return Review.builder()
                .product(product)
                .user(user)
                .rating(rating)
                .title(title)
                .content(content)
                .verifiedPurchase(true)
                .helpfulVotes(0)
                .build();
    }

    public void update(int rating, String title, String content) {
        this.rating = rating;
        this.title = title;
        this.content = content;
    }
}
