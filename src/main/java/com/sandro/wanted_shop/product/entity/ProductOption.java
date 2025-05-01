package com.sandro.wanted_shop.product.entity;

import com.sandro.wanted_shop.common.entity.BaseEntity;
import com.sandro.wanted_shop.product.entity.relation.ProductOptionGroup;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "product_options")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup optionGroup;

    @Column(nullable = false, length = 100)
    private String name;

    private BigDecimal additionalPrice;

    private String sku;

    private Integer stock;

    private Integer displayOrder; // order > 0

    @OneToMany(mappedBy = "option")
    private List<ProductImage> images;

    @Builder
    public ProductOption(ProductOptionGroup optionGroup, String name, BigDecimal additionalPrice,
                         String sku, Integer stock, Integer displayOrder, List<ProductImage> images) {
        this.optionGroup = optionGroup;
        this.name = name;
        this.additionalPrice = Optional.ofNullable(additionalPrice).orElse(BigDecimal.ZERO);
        this.sku = sku;
        this.stock = Optional.ofNullable(stock).orElse(0);
        this.displayOrder = Optional.ofNullable(displayOrder).orElse(1);
        this.images = Optional.ofNullable(images).orElse(new ArrayList<>());

        this.validate();

        optionGroup.addOption(this);
    }

    private void validate() {
        assert optionGroup != null
                && StringUtils.hasText(this.name)
                : "optionGroup and name is required";
        assert this.displayOrder > 0
                : "displayOrder is must be greater than 0";
    }

    public void addImage(ProductImage productImage) {
        this.images.add(productImage);
    }
}