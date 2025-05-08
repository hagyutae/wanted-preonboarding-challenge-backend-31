package com.pawn.wantedcqrs.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_categories")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //    product_id: 상품 ID (FK)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    //    category_id: 카테고리 ID (FK)
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    //    is_primary: 주요 카테고리 여부
    @Column(name = "is_primary")
    private boolean isPrimary = false;

    @Builder
    protected ProductCategory(Long id, Long productId, Long categoryId, boolean isPrimary) {
        this.id = id;
        this.productId = productId;
        this.categoryId = categoryId;
        this.isPrimary = isPrimary;
    }

}
