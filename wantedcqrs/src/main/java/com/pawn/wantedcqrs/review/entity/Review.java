package com.pawn.wantedcqrs.review.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reviews")
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //    product_id: 상품 ID (FK)
    @Column(name = "product_id", nullable = false)
    private Long productId; // TODO: 연관관계 파악하기

    //    user_id: 사용자 ID (FK)
    @Column(name = "user_id", nullable = false)
    private Long userId; // / TODO: 연관관계 파악하기

    //    rating: 평점 (1-5)
    @Column(name = "rating", nullable = false)
    private Integer rating; // TODO: 값타입 만들기

    //    title: 제목
    @Column(name = "title")
    private String title;

    //    content: 내용
    @Column(name = "content")
    private String content;

    //    verified_purchase: 구매 확인 여부
    @Column(name = "verified_purchase")
    private Boolean verifiedPurchase = false;

    //    helpful_votes: 도움됨 투표 수
    @Column(name = "helpful_votes")
    private Integer helpful_votes = 0;

    protected Review(Long id, Long productId, Long userId, Integer rating, String title, String content, Boolean verifiedPurchase, Integer helpful_votes) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.rating = rating;
        this.title = title;
        this.content = content;
        this.verifiedPurchase = verifiedPurchase;
        this.helpful_votes = helpful_votes;
    }

}
