package com.example.preonboarding.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private LocalDate createAt;
    private LocalDate updateAt;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToMany(mappedBy ="products")
    private List<ProductTag> productTags = new ArrayList<>();

    @OneToMany(mappedBy ="products")
    private List<ProductCategory> productCategories = new ArrayList<>();

    @OneToMany(mappedBy = "products")
    private List<ProductPrice> productPrices = new ArrayList<>();

    @OneToMany(mappedBy = "products")
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "products")
    private List<ProductOptionGroup> productOptionGroups = new ArrayList<>();

    @OneToMany(mappedBy = "products")
    private List<Reviews> reviews = new ArrayList<>();

}
