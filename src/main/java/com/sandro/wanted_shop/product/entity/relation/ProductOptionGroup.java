package com.sandro.wanted_shop.product.entity.relation;

import com.sandro.wanted_shop.common.entity.BaseEntity;
import com.sandro.wanted_shop.product.entity.Product;
import com.sandro.wanted_shop.product.entity.ProductOption;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "product_option_groups")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionGroup extends BaseEntity implements Comparable<ProductOptionGroup> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 100)
    private String name;

    private Integer displayOrder;

    @OrderBy("displayOrder")
    @OneToMany(mappedBy = "optionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options;

    @Builder
    public ProductOptionGroup(Product product, String name, Integer displayOrder, List<ProductOption> options) {
        this.product = product;
        this.name = name;
        this.displayOrder = Optional.ofNullable(displayOrder).orElse(1);
        this.options = Optional.ofNullable(options).orElse(new ArrayList<>());

        validate();

        this.product.addOptionGroup(this);
    }

    private void validate() {
        assert this.product != null
                && StringUtils.hasText(this.name)
                : "product and name are required";
        assert this.displayOrder > 0
                : "displayOrder must be greater than 0";
    }

    public void addOption(ProductOption productOption) {
        this.options.add(productOption);
    }

    @Override
    public int compareTo(ProductOptionGroup o) {
        return this.displayOrder.compareTo(o.getDisplayOrder());
    }
}