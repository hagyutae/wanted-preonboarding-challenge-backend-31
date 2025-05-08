package com.example.demo.product.entity;

import com.example.demo.product.controller.request.AddProductImageRequest;
import com.example.demo.product.controller.request.ProductImageRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product_images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
    @Column(name = "url", columnDefinition = "VARCHAR(255)", nullable = false)
    private String url;
    @Column(name = "alt_text", columnDefinition = "VARCHAR(255)")
    private String altText;
    @Column(name = "is_primary", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPrimary;
    @Column(name = "display_order", columnDefinition = "INTEGER DEFAULT 0")
    private Integer displayOrder;
    @Column(name = "option_id", columnDefinition = "BIGINT")
    private Long optionId;

    public static ProductImageEntity create(ProductImageRequest request, ProductEntity productEntity) {
        return new ProductImageEntity(
                null,
                productEntity,
                request.url(),
                request.altText(),
                request.isPrimary(),
                request.displayOrder(),
                request.optionId()
        );
    }

    public static ProductImageEntity create(AddProductImageRequest request, ProductEntity productEntity) {
        return new ProductImageEntity(
                null,
                productEntity,
                request.url(),
                request.altText(),
                request.isPrimary(),
                request.displayOrder(),
                request.optionId()
        );
    }
}
