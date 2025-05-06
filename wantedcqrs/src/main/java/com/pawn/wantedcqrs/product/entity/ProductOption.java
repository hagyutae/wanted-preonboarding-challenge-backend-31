package com.pawn.wantedcqrs.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_options")
@Builder
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //    option_group_id: 옵션 그룹 ID (FK)
    @Column(name = "option_group_id", nullable = false)
    private Long optionGroupId;

    //    name: 옵션명 (예: "빨강", "XL")
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    //    additional_price: 추가 가격
    @Column(name = "additional_price", precision = 12, scale = 2)
    private BigDecimal additionalPrice;

    //    sku: 재고 관리 코드
    @Column(name = "sku")
    private String sku;

    //    stock: 재고 수량
    @Column(name = "stock", nullable = false)
    private Integer stock;

    //    display_order: 표시 순서
    @Column(name = "display_order", nullable = false)
    private Integer display_order;

    public ProductOption(Long id, Long optionGroupId, String name, BigDecimal additionalPrice, String sku, Integer stock, Integer display_order) {
        this.id = id;
        this.optionGroupId = optionGroupId;
        this.name = name;
        this.additionalPrice = additionalPrice;
        this.sku = sku;
        this.stock = stock;
        this.display_order = display_order;
    }

}
