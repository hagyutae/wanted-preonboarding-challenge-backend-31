package com.psh10066.commerce.domain.model.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "product_prices")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "base_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "sale_price", precision = 12, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "cost_price", precision = 12, scale = 2)
    private BigDecimal costPrice;

    @Column(length = 3)
    private String currency = "KRW";

    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate;

    @Builder
    public ProductPrice(Product product, BigDecimal basePrice, BigDecimal salePrice, BigDecimal costPrice, String currency, BigDecimal taxRate) {
        this.product = product;
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.currency = currency;
        this.taxRate = taxRate;
    }

    public void update(BigDecimal basePrice, BigDecimal salePrice, BigDecimal costPrice, String currency, BigDecimal taxRate) {
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.currency = currency;
        this.taxRate = taxRate;
    }

    public Integer calculateDiscountPercentage() {
        if (salePrice == null) {
            return 0;
        }
        return salePrice.subtract(basePrice).multiply(BigDecimal.valueOf(100)).divide(basePrice, 0, RoundingMode.HALF_UP).intValue();
    }
}