package com.example.demo.review.entity;

import com.example.demo.product.entity.ProductEntity;
import com.example.demo.review.controller.request.AddReviewRequest;
import com.example.demo.review.controller.request.UpdateReviewRequest;
import com.example.demo.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @Column(name = "rating", columnDefinition = "INTEGER CHECK (rating BETWEEN 1 AND 5)", nullable = false)
    private Integer rating;
    @Column(name = "title", columnDefinition = "VARCHAR(255)")
    private String title;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
    @Column(name = "verified_purchase", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean verifiedPurchase;
    @Column(name = "helpful_votes", columnDefinition = "INTEGER DEFAULT 0")
    private Integer helpfulVotes;

    // TODO : UserEntity 추가
    public static ReviewEntity create(AddReviewRequest addReviewRequest, ProductEntity productEntity) {
        return new ReviewEntity(
                null,
                productEntity,
                null,
                addReviewRequest.rating(),
                addReviewRequest.title(),
                addReviewRequest.content(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                true,
                0
        );
    }

    public void update(UpdateReviewRequest request) {
        this.title = request.title();
        this.content = request.content();
        this.rating = request.rating();
        this.updatedAt = LocalDateTime.now();
    }
}
