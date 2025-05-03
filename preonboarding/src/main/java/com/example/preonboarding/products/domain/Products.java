package com.example.preonboarding.products.domain;

import com.example.preonboarding.brands.domain.Brands;
import com.example.preonboarding.categories.domain.ProductCategories;
import com.example.preonboarding.details.domain.ProductDetails;
import com.example.preonboarding.images.domain.ProductImages;
import com.example.preonboarding.options.domain.ProductOptionGroup;
import com.example.preonboarding.price.domain.ProductPrices;
import com.example.preonboarding.request.ProductsRequest;
import com.example.preonboarding.reviews.domain.Reviews;
import com.example.preonboarding.seller.domain.Sellers;
import com.example.preonboarding.tags.domain.ProductTags;
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

    @OneToMany(mappedBy ="products",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductTags> productTags = new ArrayList<>();

    @OneToMany(mappedBy ="products", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductCategories> productCategories = new ArrayList<>();

    @OneToOne(mappedBy = "products", cascade = CascadeType.ALL,orphanRemoval = true)
    private ProductPrices productPrices;

    @OneToOne(mappedBy = "products",cascade = CascadeType.ALL,orphanRemoval = true)
    private ProductDetails productDetails;

    @OneToMany(mappedBy = "products",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductImages> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "products",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductOptionGroup> productOptionGroups = new ArrayList<>();

    @OneToMany(mappedBy = "products",cascade = CascadeType.ALL, orphanRemoval = true)
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
        if (details != null) {
            details.setProducts(this);
        }
    }

    public void setProductPrices(ProductPrices productPrices) {
        this.productPrices = productPrices;
        if (productPrices != null) {
            productPrices.setProducts(this);
        }
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
