package com.challenge.onboarding.product.domain;

import com.challenge.onboarding.product.service.dto.request.CreateProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

@Entity
@Table(name = "product_prices")
@Builder
@AllArgsConstructor
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "base_price", precision = 12, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "sale_price", precision = 12, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "cost_price", precision = 12, scale = 2)
    private BigDecimal costPrice;

    @Column(length = 3)
    private String currency = "KRW";

    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate;

    protected ProductPrice() {}

    public static ProductPrice from(CreateProductRequest request, Product product) {
        return ProductPrice.builder()
                .product(product)
                .basePrice(BigDecimal.valueOf(request.price().basePrice()))
                .salePrice(BigDecimal.valueOf(request.price().salePrice()))
                .costPrice(BigDecimal.valueOf(request.price().costPrice()))
                .currency(request.price().currency())
                .taxRate(BigDecimal.valueOf(request.price().taxRate()))
                .build();

    }
}
