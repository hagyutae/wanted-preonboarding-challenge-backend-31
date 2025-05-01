package com.example.preonboarding.domain;

import com.example.preonboarding.request.ProductsRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
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
    private LocalDateTime updatedAt;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brands brands;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Sellers sellers;

    @OneToMany(mappedBy ="products",cascade = CascadeType.ALL)
    private List<ProductTags> productTags = new ArrayList<>();

    @OneToMany(mappedBy ="products", cascade = CascadeType.ALL)
    private List<ProductCategories> productCategories = new ArrayList<>();

    @OneToOne(mappedBy = "products", cascade = CascadeType.ALL)
    private ProductPrices productPrices;

    @OneToOne(mappedBy = "products",cascade = CascadeType.ALL)
    private ProductDetails productDetails;

    @OneToMany(mappedBy = "products",cascade = CascadeType.ALL)
    private List<ProductImages> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "products",cascade = CascadeType.ALL)
    private List<ProductOptionGroup> productOptionGroups = new ArrayList<>();

    @OneToMany(mappedBy = "products")
    private List<Reviews> reviews = new ArrayList<>();

    public void updateFrom(ProductsRequest request, Brands brands, Sellers sellers) {
        this.name = request.getName();
        this.slug = request.getSlug();
        this.shortDescription = request.getShortDescription();
        this.fullDescription = request.getFullDescription();
        this.status = request.getStatus();
        this.updatedAt = LocalDateTime.now();
        this.brands = brands;
        this.sellers = sellers;
    }


    public void setBrands(Brands brands) {
        this.brands = brands;
    }

    public void setSellers(Sellers sellers) {
        this.sellers = sellers;
    }

    public void setProductDetails(ProductDetails details) {
        this.productDetails = details;
        details.setProducts(this);
    }

    public void setProductPrices(ProductPrices productPrices) {
        this.productPrices = productPrices;
        productPrices.setProducts(this);
    }

    public void setProductImages(List<ProductImages> productImages) {
        this.productImages = productImages;
        if(productImages != null){
            productImages.stream().forEach(images -> images.setProducts(this));
        }
    }

    public void setProductCategories(List<ProductCategories> productCategories) {
        this.productCategories = productCategories;
    }

    public void setProductOptionGroups(List<ProductOptionGroup> productOptionGroups) {
        this.productOptionGroups = productOptionGroups;
    }

    public void setProductTags(List<ProductTags> productTags) {
        this.productTags = productTags;
    }

    public static Products from(ProductsRequest request) {
        return Products.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .shortDescription(request.getShortDescription())
                .fullDescription(request.getFullDescription())
                .status(request.getStatus())
        .build();
    }
}
