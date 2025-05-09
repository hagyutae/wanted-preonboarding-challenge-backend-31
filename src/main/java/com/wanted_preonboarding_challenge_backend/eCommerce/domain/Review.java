package com.wanted_preonboarding_challenge_backend.eCommerce.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Long userId;  // 유저 엔티티가 별도로 없으므로 ID로 처리
    private Integer rating;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean verifiedPurchase;
    private Integer helpfulVotes;


    public void updateReview(Integer rating, String title, String content){
        this.rating = rating;
        this.title = title;
        this.content = content;
    }

    public void writeReview(Product product){
        this.product = product;
    }
}
