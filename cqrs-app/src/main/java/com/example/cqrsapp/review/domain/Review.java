package com.example.cqrsapp.review.domain;

import com.example.cqrsapp.product.domain.Product;
import com.example.cqrsapp.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String content;

    Boolean verifiedPurchase;

    Integer helpfulVotes;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();



    @Builder
    public Review(Product product, User user, String title, Integer rating, String content) {
        this.product = product;
        this.user = user;
        this.title = title;
        this.rating = rating;
        this.content = content;
    }

    public void changeReview(String title, Integer rating, String content) {
        this.title = title;
        this.rating = rating;
        this.content = content;
    }
}