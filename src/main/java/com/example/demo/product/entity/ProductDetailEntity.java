package com.example.demo.product.entity;

import com.example.demo.product.controller.request.ProductDetailRequest;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Getter
@Table(name = "product_details")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "weight", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal weight;
    @Type(JsonType.class)
    @Column(name = "dimensions", columnDefinition = "JSONB")
    private Map<String, Object> dimensions;
    @Column(name = "materials", columnDefinition = "text")
    private String materials;
    @Column(name = "country_of_origin", columnDefinition = "VARCHAR(100)")
    private String countryOfOrigin;
    @Column(name = "warranty_info", columnDefinition = "TEXT")
    private String warrantyInfo;
    @Column(name = "care_instructions", columnDefinition = "TEXT")
    private String careInstructions;
    @Type(JsonType.class)
    @Column(name = "additional_info", columnDefinition = "JSONB")
    private Map<String, Object> additionalInfo;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    public static ProductDetailEntity create(ProductDetailRequest request) {
        return new ProductDetailEntity(
                null,
                request.weight(),
                request.dimensions(),
                request.materials(),
                request.countryOfOrigin(),
                request.warrantyInfo(),
                request.careInstructions(),
                request.additionalInfo(),
                null
        );
    }

    public void connectProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public void update(ProductDetailRequest detail) {
        this.weight = detail.weight();
        this.dimensions = detail.dimensions();
        this.materials = detail.materials();
        this.countryOfOrigin = detail.countryOfOrigin();
        this.additionalInfo = detail.additionalInfo();
        this.careInstructions = detail.careInstructions();
        this.warrantyInfo = detail.warrantyInfo();
    }
}
