package com.sandro.wanted_shop.product.entity.relation;

import com.sandro.wanted_shop.category.Category;
import com.sandro.wanted_shop.common.entity.BaseEntity;
import com.sandro.wanted_shop.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Table(name = "product_categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private Boolean isPrimary;

    @Builder
    public ProductCategory(Product product, Category category, Boolean isPrimary) {
        this.product = product;
        this.category = category;
        this.isPrimary = Optional.ofNullable(isPrimary).orElse(false);

        validate();

        this.category.addProductCategory(this);
    }

    public static ProductCategory of(Product product, Category category) {
        return of(product, category, false);
    }

    public static ProductCategory of(Product product, Category category, boolean isPrimary) {
        return ProductCategory.builder()
                .product(product)
                .category(category)
                .isPrimary(isPrimary)
                .build();
    }

    private void validate() {
        assert this.product != null
                && this.category != null
                : "product and category are required";
    }
}