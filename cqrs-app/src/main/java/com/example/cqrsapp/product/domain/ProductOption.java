package com.example.cqrsapp.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "product_options")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup optionGroup;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(precision = 10, scale = 2)
    private BigDecimal additionalPrice;

    @Column(length = 100)
    private String sku;

    private Integer stock;

    private Integer displayOrder;

    @Builder
    public ProductOption(ProductOptionGroup optionGroup, String name, BigDecimal additionalPrice, String sku, Integer stock, Integer displayOrder ) {
        this.optionGroup = optionGroup;
        this.name = name;
        this.additionalPrice = additionalPrice;
        this.sku  = sku;
        this.stock = stock;
        this.displayOrder = displayOrder;
    }
}