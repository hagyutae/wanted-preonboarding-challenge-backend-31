package com.challenge.onboarding.product.domain;

import com.challenge.onboarding.product.service.dto.request.CreateProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "product_images")
@Builder
@AllArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String url;

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Column(name = "display_order")
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private ProductOption option;

    protected ProductImage() {}

    public static List<ProductImage> from(Product product, CreateProductRequest request) {
        return request.images().stream()
                .map(image -> ProductImage.builder()
                        .product(product)
                        .url(image.url())
                        .altText(image.altText())
                        .isPrimary(image.isPrimary())
                        .displayOrder(image.displayOrder())
                        .build())
                .toList();
    }
}