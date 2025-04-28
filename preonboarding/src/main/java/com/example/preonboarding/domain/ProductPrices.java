package com.example.preonboarding.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_prices")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductPrices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    private int basePrice;

    private int salePrice;

    private int costPrice;

    private String currency;

    private double tax_rate;
}
