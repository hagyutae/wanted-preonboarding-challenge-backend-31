package com.wanted.mono.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "product_images")
public class ProductImage {

    // 이미지 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 상품 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 이미지 URL
    @Column(length = 255, nullable = false)
    private String url;

    // 대체 텍스트
    @Column(length = 255)
    private String altText;

    // 대표 이미지 여부
    private Boolean isPrimary = false;

    // 표시 순서
    private Integer displayOrder = 0;

    // 연관된 옵션 ID (nullable)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = true)
    private ProductOption option;
}
