package com.ecommerce.products.infra.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Comment("상품 가격")
@Entity
@Table(name = "product_prices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPrice {
    @Comment("가격 ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("상품 ID")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Comment("기본 가격")
    @Column(name = "base_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal basePrice;

    @Comment("할인 가격")
    @Column(name = "sale_price", precision = 19, scale = 2)
    private BigDecimal salePrice;

    @Comment("원가(관리용)")
    @Column(name = "cost_price", precision = 19, scale = 2)
    private BigDecimal costPrice;

    @Comment("통화 (기본값 : KRW)")
    @Column(length = 3, nullable = false)
    @Builder.Default
    private String currency = "KRW";

    @Comment("세율")
    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate;

    // Helper method to calculate discount percentage
    @Transient
    public Integer getDiscountPercentage() {
        if (basePrice == null || salePrice == null || basePrice.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }

        if (salePrice.compareTo(basePrice) >= 0) {
            return 0;
        }

        BigDecimal discount = basePrice.subtract(salePrice);
        BigDecimal percentage = discount.multiply(new BigDecimal("100")).divide(basePrice, 0, BigDecimal.ROUND_HALF_UP);

        return percentage.intValue();
    }

}