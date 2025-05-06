package com.shopping.mall.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_price")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "base_price")
    private Integer basePrice;

    @Column(name = "sale_price")
    private Integer salePrice;

    @Column(name = "cost_price")
    private Integer costPrice;

    private String currency;

    @Column(name = "tax_rate")
    private Integer taxRate;
}
