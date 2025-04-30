package com.wanted.mono.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "sellers")
public class Seller {

    // 판매자 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 판매자명
    @Column(length = 100, nullable = false)
    private String name;

    // 설명
    private String description;

    // 로고 이미지 URL
    @Column(length = 255)
    private String logoUrl;

    // 평점
    @Column(precision = 3, scale = 2)
    private BigDecimal rating;

    // 연락처 이메일
    @Column(length = 100)
    private String contactEmail;

    // 연락처 전화번호
    @Column(length = 20)
    private String contactPhone;

    // 등록일
    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}
