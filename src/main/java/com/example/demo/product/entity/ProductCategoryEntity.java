package com.example.demo.product.entity;

import com.example.demo.category.entity.CategoryEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product_categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;
    @Column(name = "is_primary", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPrimary;

    public static ProductCategoryEntity create(
            ProductEntity productEntity,
            CategoryEntity categoryEntity,
            Boolean isPrimary
    ) {
        return new ProductCategoryEntity(
                null,
                productEntity,
                categoryEntity,
                isPrimary
        );
    }
}
