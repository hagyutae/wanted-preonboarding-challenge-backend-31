package com.sandro.wanted_shop.domain;

import com.sandro.wanted_shop.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_options")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup optionGroup;

    @Column(nullable = false, length = 100)
    private String name;

    private BigDecimal additionalPrice = BigDecimal.ZERO;

    private String sku;

    private Integer stock = 0;

    private Integer displayOrder = 0;

    @OneToMany(mappedBy = "option")
    private List<ProductImage> images = new ArrayList<>();
} 