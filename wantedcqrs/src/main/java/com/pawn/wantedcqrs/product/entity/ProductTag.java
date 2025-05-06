package com.pawn.wantedcqrs.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_tags")
@Builder
public class ProductTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //    product_id: 상품 ID (FK)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    //    tag_id: 태그 ID (FK)
    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    public ProductTag(Long id, Long productId, Long tagId) {
        this.id = id;
        this.productId = productId;
        this.tagId = tagId;
    }

}
