package com.wanted.mono.domain.product.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.mono.domain.product.dto.AdditionalInfo;
import com.wanted.mono.domain.product.dto.Dimension;
import com.wanted.mono.domain.product.dto.ProductDetailRequest;
import com.wanted.mono.global.AdditionalInfoConverter;
import com.wanted.mono.global.DimensionConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 상품 상세 정보를 나타내는 엔티티
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "product_details")
public class ProductDetail {
    // 상세 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 상품 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // 무게
    @Column(precision = 10, scale = 2)
    private BigDecimal weight;

    // 크기 (JSON)
    @Column(columnDefinition = "JSONB")
    @Convert(converter = DimensionConverter.class)
    private Dimension dimensions;

    // 소재 정보
    private String materials;

    // 원산지
    @Column(length = 100)
    private String countryOfOrigin;

    // 보증 정보
    private String warrantyInfo;

    // 관리 지침
    private String careInstructions;

    // 추가 정보 (JSONB)
    @Column(columnDefinition = "JSONB")
    @Convert(converter = AdditionalInfoConverter.class)
    private AdditionalInfo additionalInfo;

    // -----------------------------------------

    public static ProductDetail of(ProductDetailRequest request) {
        ProductDetail detail = new ProductDetail();
        detail.weight = request.getWeight();
        detail.materials = request.getMaterials();
        detail.countryOfOrigin = request.getCountryOfOrigin();
        detail.warrantyInfo = request.getWarrantyInfo();
        detail.careInstructions = request.getCareInstructions();
        detail.dimensions = request.getDimensions();
        detail.additionalInfo = request.getAdditionalInfo();

        return detail;
    }

    public void addProduct(Product product) {
        this.product = product;
    }
}
