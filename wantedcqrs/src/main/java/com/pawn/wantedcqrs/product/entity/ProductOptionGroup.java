package com.pawn.wantedcqrs.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_option_groups")
@Builder
public class ProductOptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //    product_id: 상품 ID (FK)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    //    name: 옵션 그룹명 (예: "색상", "사이즈")
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    //    display_order: 표시 순서
    @Column(name = "display_order")
    private int displayOrder;

    public ProductOptionGroup(Long id, Long productId, String name, int displayOrder) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.displayOrder = displayOrder;
    }

}
