package com.wanted.mono.domain.product.entity;

import com.wanted.mono.domain.product.dto.request.ProductPriceRequest;
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
    @Column(length = 3, columnDefinition = "VARCHAR(3) DEFAULT 'KRW'")
    private String currency;

    // 세율
    @Column(precision = 5, scale = 2)
    private BigDecimal taxRate;

    // -----------------------------------

    public static ProductPrice of(ProductPriceRequest request) {
        ProductPrice productPrice = new ProductPrice();
        productPrice.basePrice = request.getBasePrice();
        productPrice.salePrice = request.getSalePrice();
        productPrice.costPrice = request.getCostPrice();
        productPrice.currency = request.getCurrency();
        productPrice.taxRate = request.getTaxRate();
        return productPrice;
    }

    public void addProduct(Product product) {
        this.product = product;
    }

    public void update(ProductPriceRequest request) {
        this.basePrice = request.getBasePrice();
        this.salePrice = request.getSalePrice();
        this.costPrice = request.getCostPrice();
        this.currency = request.getCurrency();
        this.taxRate = request.getTaxRate();
    }
}
