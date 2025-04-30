package com.example.wanted_preonboarding_challenge_backend_31.domain.model.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "product_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(precision = 10, scale = 2)
    private BigDecimal weight;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSONB")
    private Map<String, Object> dimensions;

    @Column(columnDefinition = "TEXT")
    private String materials;

    @Column(length = 100)
    private String countryOfOrigin;

    @Column(columnDefinition = "TEXT")
    private String warrantyInfo;

    @Column(columnDefinition = "TEXT")
    private String careInstructions;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSONB")
    private Map<String, Object> additionalInfo;


    public static ProductDetail create(Product product, BigDecimal weight, Map<String, Object> dimensions,
                                       String materials, String countryOfOrigin, String warrantyInfo,
                                       String careInstructions, Map<String, Object> additionalInfo) {
        return ProductDetail.builder()
                .product(product)
                .weight(weight)
                .dimensions(dimensions)
                .materials(materials)
                .countryOfOrigin(countryOfOrigin)
                .warrantyInfo(warrantyInfo)
                .careInstructions(careInstructions)
                .additionalInfo(additionalInfo)
                .build();
    }
}
