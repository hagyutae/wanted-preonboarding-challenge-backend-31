package com.june.ecommerce.domain.productcategory;

import com.june.ecommerce.domain.category.Category;
import com.june.ecommerce.domain.product.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private boolean isPrimary = false;

    @Builder
    public ProductCategory(Product product, Category category, boolean isPrimary) {
        this.product = product;
        this.category = category;
        this.isPrimary = isPrimary;
    }
}
