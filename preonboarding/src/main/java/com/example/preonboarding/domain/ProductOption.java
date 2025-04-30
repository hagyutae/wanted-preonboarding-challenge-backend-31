package com.example.preonboarding.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_options")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup optionGroups;

    @OneToMany(mappedBy = "options")
    private List<ProductImages> productImages = new ArrayList<>();

    private String name;
    private double additionalPrice;
    private String sku;
    private int stock;
    private int displayOrder;
}
