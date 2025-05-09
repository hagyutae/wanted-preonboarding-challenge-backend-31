package com.preonboarding.challenge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_prices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Column(name = "base_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "sale_price", precision = 19, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "cost_price", precision = 19, scale = 2)
    private BigDecimal costPrice;

    @Column(length = 3, nullable = false)
    @Builder.Default
    private String currency = "KRW";

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
