package com.example.preonboarding.options.domain;

import com.example.preonboarding.products.domain.Products;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_option_groups")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    @OneToMany(mappedBy = "optionGroups")
    private List<ProductOption> optionGroups = new ArrayList<>();

    private String name;
    private int displayOrder;

    public void setProducts(Products products) {
        this.products = products;
    }
    public void addOption(ProductOption option) {
        this.optionGroups.add(option);
        option.setOptionGroups(this);
    }
}
