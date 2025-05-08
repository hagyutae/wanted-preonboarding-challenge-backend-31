package com.pawn.wantedcqrs.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_prices")
@Builder
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //    product_id: 상품 ID (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    //    base_price: 기본 가격
    @Column(name = "base_price", precision = 12, scale = 2, nullable = false)
    private BigDecimal basePrice;

    //    sale_price: 할인 가격
    @Column(name = "sale_price", precision = 12, scale = 2)
    private BigDecimal salePrice;

    //    cost_price: 원가 (관리용)
    @Column(name = "cost_price", precision = 12, scale = 2)
    private BigDecimal costPrice;

    //    currency: 통화 (기본값 KRW)
    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    //    tax_rate: 세율
    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate;

    protected ProductPrice(Long id, Product product, BigDecimal basePrice, BigDecimal salePrice, BigDecimal costPrice, Currency currency, BigDecimal taxRate) {
        this.id = id;
        this.product = product;
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.currency = currency;
        this.taxRate = taxRate;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
