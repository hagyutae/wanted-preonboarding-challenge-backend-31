package com.ecommerce.products.infra.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Comment("상품 상세")
@Entity
@Table(name = "product_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail {
    @Comment("상세 ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("상품 ID")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    @Comment("무게")
    @Column(name = "weight", precision = 10, scale = 2)
    private BigDecimal weight;

    @Comment("크기 (JSON: {\"width\": float, \"height\": float, \"depth\": float})")
    @Column(name = "dimensions")
    private String dimensions;

    @Comment("소재 정보")
    @Column(name = "materials", length = Integer.MAX_VALUE)
    private String materials;

    @Comment("원산지")
    @Column(name = "country_of_origin", length = 100)
    private String countryOfOrigin;

    @Comment("보증 정보")
    @Column(name = "warranty_info", length = 1000)
    private String warrantyInfo;

    @Comment("관리 지침")
    @Column(name = "care_instructions", length = 1000)
    private String careInstructions;

    @Comment("추가정보(JSONB)")
    @Column(name = "additional_info", columnDefinition = "jsonb")
    private String additionalInfo;

}