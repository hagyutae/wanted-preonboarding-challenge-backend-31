package com.shopping.mall.review.entity;

import com.shopping.mall.common.entity.BaseEntity;
import com.shopping.mall.product.entity.Product;
import com.shopping.mall.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer rating;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "verified_purchase")
    private Boolean verifiedPurchase;

    @Column(name = "helpful_votes")
    private Integer helpfulVotes;

    @PrePersist
    public void onCreate() {
        this.helpfulVotes = 0;
    }

    public void update(String title, String content, Integer rating) {
        this.title = title;
        this.content = content;
        this.rating = rating;
    }
}