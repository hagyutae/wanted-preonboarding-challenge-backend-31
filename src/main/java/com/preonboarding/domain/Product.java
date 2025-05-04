package com.preonboarding.domain;

import com.preonboarding.dto.request.product.ProductCreateRequestDto;
import com.preonboarding.dto.request.product.ProductEditRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false,nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Column(name = "full_description", columnDefinition = "TEXT")
    private String fullDescription;

    @Column(nullable = false, length = 20)
    private String status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private ProductDetail productDetail;

    @OneToOne(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private ProductPrice productPrice;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    @Builder.Default
    private List<ProductCategory> productCategoryList = new ArrayList<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    @Builder.Default
    private List<ProductOptionGroup> productOptionGroupList = new ArrayList<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    @Builder.Default
    private List<ProductImage> productImageList = new ArrayList<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    @Builder.Default
    private List<ProductTag> productTagList = new ArrayList<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    @Builder.Default
    private List<Review> reviewList = new ArrayList<>();

    public static Product from(Seller seller, Brand brand, ProductCreateRequestDto dto) {
        return Product.builder()
                .seller(seller)
                .brand(brand)
                .name(dto.getName())
                .slug(dto.getSlug())
                .shortDescription(dto.getShortDescription())
                .fullDescription(dto.getFullDescription())
                .status(dto.getStatus())
                .build();
    }

    public void updateProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList.clear();
        this.productCategoryList.addAll(productCategoryList);
    }

    public void updateProductTagList(List<ProductTag> productTagList) {
        this.productTagList.clear();
        this.productTagList.addAll(productTagList);
    }

    public void updateProductImageList(List<ProductImage> productImageList) {
        this.productImageList.clear();
        this.productImageList.addAll(productImageList);
    }

    public void updateProductOptionGroup(List<ProductOptionGroup> productOptionGroupList) {
        this.productOptionGroupList.clear();
        this.productOptionGroupList.addAll(productOptionGroupList);
    }

    public void updateProduct(ProductEditRequestDto dto) {
        this.name = dto.getName();
        this.slug = dto.getSlug();
        this.shortDescription = dto.getShortDescription();
        this.fullDescription = dto.getFullDescription();
        this.status = dto.getStatus();
    }

    public void updateProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public void updateProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }

    public void updateSeller(Seller seller) {
        this.seller = seller;
    }

    public void updateBrand(Brand brand) {
        this.brand = brand;
    }

    public void deleteProduct() {
        this.status = "DELETED";
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product that = (Product) o;
        if (id == null && that.id == null) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
