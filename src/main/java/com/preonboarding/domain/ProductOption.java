package com.preonboarding.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product_options")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup optionGroup;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "additional_price", precision = 12, scale = 2)
    private BigDecimal additionalPrice;

    @Column(length = 100)
    private String sku;

    @Column
    private Integer stock;

    @Column(name = "display_order")
    private Integer displayOrder;
}
