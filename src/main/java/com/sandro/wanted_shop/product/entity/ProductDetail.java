package com.sandro.wanted_shop.product.entity;

import com.sandro.wanted_shop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "product_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetail extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal weight;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Dimensions dimensions;

    private String materials;

    private String countryOfOrigin;

    private String warrantyInfo;

    private String careInstructions;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> additionalInfo;

    @Builder
    public ProductDetail(Product product, BigDecimal weight, Dimensions dimensions, String materials, String countryOfOrigin,
                         String warrantyInfo, String careInstructions, Map<String, Object> additionalInfo) {
        this.product = product;
        this.weight = weight;
        this.dimensions = dimensions;
        this.materials = materials;
        this.countryOfOrigin = countryOfOrigin;
        this.warrantyInfo = warrantyInfo;
        this.careInstructions = careInstructions;
        this.additionalInfo = additionalInfo;

        validate();

        this.product.updateDetail(this);
    }

    private void validate() {
        assert product != null
                : "Product must not be null";
    }

    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Dimensions {
        private float depth;
        private float width;
        private float height;
    }
}