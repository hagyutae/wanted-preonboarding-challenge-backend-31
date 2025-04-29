package com.wanted.mono.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "product_prices")
public class ProductPrice {
    // 가격 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_price_id")
    private Long id;

    // 상품 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 기본 가격
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal basePrice;

    // 할인 가격
    @Column(precision = 12, scale = 2)
    private BigDecimal salePrice;

    // 원가 (관리용)
    @Column(precision = 12, scale = 2)
    private BigDecimal costPrice;

    // 통화 (기본값 KRW)
    private String currency = "KRW";

    // 세율
    @Column(precision = 5, scale = 2)
    private BigDecimal taxRate;
}
