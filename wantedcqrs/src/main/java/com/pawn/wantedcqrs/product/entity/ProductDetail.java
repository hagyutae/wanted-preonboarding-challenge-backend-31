package com.pawn.wantedcqrs.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_details")
@Builder
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //    product_id: 상품 ID (FK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    //    weight: 무게
    @Column(name = "weight", precision = 12, scale = 2)
    private BigDecimal weight;

    //    dimensions: 크기 (JSON)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dimensions", columnDefinition = "jsonb")
    private Dimensions dimensions;

    //    materials: 소재 정보
    @Column(name = "materials")
    private String materials;

    //    country_of_origin: 원산지
    @Column(name = "country_of_origin" , length = 500)
    private String countryOfOrigin;

    //    warranty_info: 보증 정보
    @Column(name = "warranty_info")
    private String warrantyInfo;

    //    care_instructions: 관리 지침
    @Column(name = "care_instructions")
    private String careInstructions;

    //    additional_info: 추가 정보 (JSONB)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "additional_info", columnDefinition = "jsonb")
    private AdditionalInfo additionalInfo;

    protected ProductDetail(Long id, Product product, BigDecimal weight, Dimensions dimensions, String materials, String countryOfOrigin, String warrantyInfo, String careInstructions, AdditionalInfo additionalInfo) {
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

    @Getter
    @Builder
    public static class Dimensions {

        @Column(name = "width")
        private int width;

        @Column(name = "height")
        private int height;

        @Column(name = "depth")
        private int depth;

        protected Dimensions(int width, int height, int depth) {
            this.width = width;
            this.height = height;
            this.depth = depth;
        }

    }

    @Getter
    @Builder
    public static class AdditionalInfo {

        @Column(name = "assembly_required")
        private boolean assemblyRequired;

        @Column(name = "assembly_time")
        private String assemblyTime;

        protected AdditionalInfo(boolean assemblyRequired, String assemblyTime) {
            this.assemblyRequired = assemblyRequired;
            this.assemblyTime = assemblyTime;
        }

    }

}

