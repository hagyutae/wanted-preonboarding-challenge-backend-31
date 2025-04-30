package com.wanted.mono.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 상품 상세 정보를 나타내는 엔티티
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "product_details")
public class ProductDetail {
    // 상세 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 상품 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 무게
    @Column(precision = 10, scale = 2)
    private BigDecimal weight;

    // 크기 (JSON)
    @Column(columnDefinition = "JSONB")
    private String dimensions;

    // 소재 정보
    private String materials;

    // 원산지
    @Column(length = 100)
    private String countryOfOrigin;

    // 보증 정보
    private String warrantyInfo;

    // 관리 지침
    private String careInstructions;

    // 추가 정보 (JSONB)
    @Column(columnDefinition = "JSONB")
    private String additionalInfo;
}
