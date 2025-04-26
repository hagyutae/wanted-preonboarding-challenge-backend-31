package com.example.preonboarding.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_price")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    private int basePrice;

    private int salesPrice;

    private int costPrice;

    private String current;

    private double tax_rate;
}
