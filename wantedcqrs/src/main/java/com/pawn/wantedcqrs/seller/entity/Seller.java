package com.pawn.wantedcqrs.seller.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //    name: 판매자명
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    //    description: 설명
    @Column(name = "description")
    private String description;

    //    logo_url: 로고 이미지 URL
    @Column(name = "logo_url")
    private String logoUrl;

    //    rating: 평점
    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal rating;

    //    contact_email: 연락처 이메일
    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    //    contact_phone: 연락처 전화번호
    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // TODO: 더 좋은 방법 찾기

    @Builder
    protected Seller(Long id, String name, String description, String logoUrl, BigDecimal rating, String contactEmail, String contactPhone, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
        this.rating = rating;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.createdAt = createdAt;
    }

}
