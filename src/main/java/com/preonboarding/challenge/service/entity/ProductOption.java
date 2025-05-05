package com.preonboarding.challenge.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false)
    private ProductOptionGroup optionGroup;

    @Column(nullable = false)
    private String name;

    @Column(name = "additional_price", precision = 19, scale = 2)
    private BigDecimal additionalPrice;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();
}