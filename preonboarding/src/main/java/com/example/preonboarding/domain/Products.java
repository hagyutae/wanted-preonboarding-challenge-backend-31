package com.example.preonboarding.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brands brands;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Sellers sellers;

    @OneToMany(mappedBy ="products")
    private List<ProductTags> productTags = new ArrayList<>();

    @OneToMany(mappedBy ="products")
    private List<ProductCategories> productCategories = new ArrayList<>();

    @OneToOne(mappedBy = "products")
    private ProductPrices productPrices;

    @OneToOne(mappedBy = "products")
    private ProductDetails productDetails;

    @OneToMany(mappedBy = "products")
    private List<ProductImages> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "products")
    private List<ProductOptionGroup> productOptionGroups = new ArrayList<>();

    @OneToMany(mappedBy = "products")
    private List<Reviews> reviews = new ArrayList<>();

}
