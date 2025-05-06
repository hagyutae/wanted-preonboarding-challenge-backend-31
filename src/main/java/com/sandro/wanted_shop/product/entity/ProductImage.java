package com.sandro.wanted_shop.product.entity;

import com.sandro.wanted_shop.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.StringUtils;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "product_images")
@Entity
public class ProductImage extends BaseEntity implements Comparable<ProductImage> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private String url;

    private String altText;

    private Boolean isPrimary;

    private Integer displayOrder;

    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private ProductOption option;

    @Builder
    public ProductImage(Product product, String url, String altText, Boolean isPrimary, Integer displayOrder, ProductOption option) {
        this.product = product;
        this.url = url;
        this.altText = altText;
        this.isPrimary = Optional.ofNullable(isPrimary).orElse(false);
        this.displayOrder = Optional.ofNullable(displayOrder).orElse(1);
        this.option = option;

        this.validate();

        this.product.addImage(this);

        if (this.option != null)
            this.option.addImage(this);
    }

    private void validate() {
        assert this.product != null
                && StringUtils.hasText(this.url)
                : "product and url are required";

        assert this.displayOrder > 0
                : "displayOrder must be greater than 0";
    }

    @Override
    public int compareTo(ProductImage o) {
        return this.displayOrder.compareTo(o.getDisplayOrder());
    }
}