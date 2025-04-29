package com.preonboarding.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product_details")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "product_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(columnDefinition = "jsonb")
    private String dimensions;

    @Column(columnDefinition = "TEXT")
    private String materials;

    @Column(name = "country_of_origin", length = 100)
    private String countryOfOrigin;

    @Column(name = "warranty_info", columnDefinition = "TEXT")
    private String warrantyInfo;

    @Column(name = "care_instructions", columnDefinition = "TEXT")
    private String careInstructions;

    // jsonb 타입 매핑
    @Column(name = "additional_info", columnDefinition = "jsonb")
    private String additionalInfo;
}
