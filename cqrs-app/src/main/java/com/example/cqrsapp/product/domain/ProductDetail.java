package com.example.cqrsapp.product.domain;


import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Entity
@Table(name = "product_details")
@NoArgsConstructor(access = PRIVATE)
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private BigDecimal weight;

    @Type(value = JsonBinaryType.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Dimensions dimensions;

    @Column(columnDefinition = "TEXT")
    private String materials;

    @Column(length = 100)
    private String countryOfOrigin;

    @Column(columnDefinition = "TEXT")
    private String warrantyInfo;

    @Column(columnDefinition = "TEXT")
    private String careInstructions;

    @Type(value = JsonBinaryType.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSONB")
    private Map<String, String> additionalInfo;

    @Builder
    public ProductDetail(Long id, Product product, BigDecimal weight, Dimensions dimensions, String materials, String countryOfOrigin, String warrantyInfo, String careInstructions, Map<String, String> additionalInfo) {
        this.id = id;
        this.product = product;
        this.weight = weight;
        this.dimensions = dimensions;
        this.materials = materials;
        this.countryOfOrigin = countryOfOrigin;
        this.warrantyInfo = warrantyInfo;
        this.careInstructions = careInstructions;
        this.additionalInfo = additionalInfo;
    }
}