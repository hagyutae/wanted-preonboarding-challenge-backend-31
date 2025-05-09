package com.wanted_preonboarding_challenge_backend.eCommerce.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_option")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup optionGroup;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    private String name;
    private Double additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;


    public void updateProductOption(String name, Double additionalPrice, String sku, Integer stock, Integer displayOrder){
        this.name = name;
        this.additionalPrice = additionalPrice;
        this.sku = sku;
        this.stock = stock;
        this.displayOrder = displayOrder;
    }

    public void addOptionGroup(ProductOptionGroup productOptionGroup){
        this.optionGroup = productOptionGroup;
    }
}