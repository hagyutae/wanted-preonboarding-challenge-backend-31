package com.ecommerce.products.infra.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Comment("상품 옵션")
@Entity
@Table(name = "product_options")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOption {
    @Comment("상품 옵션 ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("상품 옵션 그룹 ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id", nullable = false)
    private ProductOptionGroup optionGroup;

    @Comment("상품 옵션명")
    @Column(nullable = false)
    private String name;

    @Comment("추가 가격")
    @Column(name = "additional_price", precision = 19, scale = 2)
    private BigDecimal additionalPrice;

    @Comment("재고 관리 코드")
    @Column(nullable = false, unique = true)
    private String sku;

    @Comment("재고 수량")
    @Column(nullable = false)
    private Integer stock;

    @Comment("표시 순서")
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @Comment("연관 옵션")
    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();

}