package com.ecommerce.products.infra.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Comment("리뷰")
@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Comment("리뷰 ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("상품 ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Comment("사용자 ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Comment("평점 (1~5)")
    @Column(nullable = false)
    private Integer rating; // 1-5

    @Comment("제목")
    @Column(length = 200)
    private String title;

    @Comment("내용")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Comment("작성일")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Comment("수정일")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Comment("구매 확인 여부")
    @Column(name = "verified_purchase", nullable = false)
    private boolean verifiedPurchase;

    @Comment("도움됨 투표 수")
    @Column(name = "helpful_votes", nullable = false)
    @Builder.Default
    private Integer helpfulVotes = 0;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}