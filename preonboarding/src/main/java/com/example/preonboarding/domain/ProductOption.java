package com.example.preonboarding.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_option")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup optionGroup;

    @OneToMany(mappedBy = "options")
    private List<ProductImage> productImages = new ArrayList<>();

    private String name;
    private double addtionalPrice;
    private String sku;
    private int stock;
    private int displayOrder;
}
