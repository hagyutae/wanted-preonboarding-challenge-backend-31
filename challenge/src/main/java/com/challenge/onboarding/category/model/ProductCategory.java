package com.challenge.onboarding.category.model;

import com.challenge.onboarding.product.domain.model.Product;
import com.challenge.onboarding.product.service.dto.request.CreateProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "product_categories")
@Builder
@AllArgsConstructor
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    protected ProductCategory() {
    }

    public static ProductCategory from(
            CreateProductRequest.CategoryMapping categoryMapping,
                                       Product product,
                                       Category category
    ) {
        return ProductCategory.builder()
                .product(product)
                .category(category)
                .isPrimary(categoryMapping.isPrimary())
                .build();
    }
}
