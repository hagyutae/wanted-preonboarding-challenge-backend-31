package com.challenge.onboarding.product.domain.model;

import com.challenge.onboarding.product.service.dto.request.CreateProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@AllArgsConstructor
@Builder
@Table(name = "product_details")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Double weight;

    @Column(columnDefinition = "json")
    private String dimensions;

    private String materials;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @Column(name = "warranty_info")
    private String warrantyInfo;

    @Column(name = "care_instructions", columnDefinition = "TEXT")
    private String careInstructions;

    @Column(name = "additional_info", columnDefinition = "jsonb")
    private String additionalInfo;

    protected ProductDetail() {}

    public static ProductDetail generateProductDetail(CreateProductRequest request, Product product) {
        return ProductDetail.builder()
                .product(product)
                .weight(request.detail().weight())
                .dimensions(String.valueOf(request.detail().dimensions()))
                .materials(request.detail().materials())
                .countryOfOrigin(request.detail().countryOfOrigin())
                .warrantyInfo(request.detail().warrantyInfo())
                .careInstructions(request.detail().careInstructions())
                .additionalInfo(String.valueOf(request.detail().additionalInfo()))
                .build();
    }
}
