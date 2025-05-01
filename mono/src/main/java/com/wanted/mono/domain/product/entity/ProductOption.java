package com.wanted.mono.domain.product.entity;

import com.wanted.mono.domain.product.dto.ProductOptionRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "product_options")
public class ProductOption {

    // 옵션 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 옵션 그룹 ID (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false)
    private ProductOptionGroup optionGroup;

    // 옵션명 (예: "빨강", "XL")
    @Column(length = 100, nullable = false)
    private String name;

    // 추가 가격
    @Column(precision = 12, scale = 2)
    private BigDecimal additionalPrice = BigDecimal.ZERO;

    // 재고 관리 코드 (SKU)
    @Column(length = 100)
    private String sku;

    // 재고 수량
    private Integer stock = 0;

    // 표시 순서
    private Integer displayOrder = 0;

    // --------------------------
    public static ProductOption of(ProductOptionRequest productOptionRequest) {
        ProductOption productOption = new ProductOption();
        productOption.name = productOptionRequest.getName();
        productOption.additionalPrice = productOptionRequest.getAdditionalPrice();
        productOption.sku = productOptionRequest.getSku();
        productOption.stock = productOptionRequest.getStock();
        productOption.displayOrder = productOptionRequest.getDisplayOrder();
        return productOption;
    }

    public void addOptionGroup(ProductOptionGroup productOptionGroup) {
        this.optionGroup = productOptionGroup;
    }
}