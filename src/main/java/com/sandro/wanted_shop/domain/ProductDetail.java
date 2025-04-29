package com.sandro.wanted_shop.domain;

import com.sandro.wanted_shop.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetail extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal weight;

    @Column(columnDefinition = "jsonb")
    private String dimensions;

    private String materials;

    private String countryOfOrigin;

    private String warrantyInfo;

    private String careInstructions;

    @Column(columnDefinition = "jsonb")
    private String additionalInfo;
} 