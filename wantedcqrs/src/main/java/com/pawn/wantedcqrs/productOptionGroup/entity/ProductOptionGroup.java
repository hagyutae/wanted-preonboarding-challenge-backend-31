package com.pawn.wantedcqrs.productOptionGroup.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_option_groups")
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

    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductOption> options = new ArrayList<>();

    private ProductOptionGroup(Long id, Long productId, String name, int displayOrder) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.displayOrder = displayOrder;
    }

    @Builder
    protected ProductOptionGroup(Long id, Long productId, String name, int displayOrder, List<ProductOption> options) {
        this(id, productId, name, displayOrder);
        if (Objects.nonNull(options)) {
            options.forEach(this::addOption);
        }
    }

    public void addOption(ProductOption productOption) {
        options.add(productOption);
        productOption.setOptionGroup(this);
    }

    public void removeOption(ProductOption productOption) {
        options.remove(productOption);
        productOption.setOptionGroup(null);
    }

}
