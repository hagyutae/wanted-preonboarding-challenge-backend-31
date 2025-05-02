package com.dino.cqrs_challenge.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product_options", schema = "commerce")
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false)
    private ProductOptionGroup productOptionGroup;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(precision = 12, scale = 2)
    private BigDecimal additionalPrice = BigDecimal.ZERO;

    @Column(length = 100)
    private String sku;

    private Integer stock = 0;

    private Integer displayOrder = 0;
}
