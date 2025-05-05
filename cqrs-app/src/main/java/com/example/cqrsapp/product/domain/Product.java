package com.example.cqrsapp.product.domain;

import com.example.cqrsapp.seller.domain.Seller;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "products")
@Getter
@SQLDelete(sql = "UPDATE Products SET status = 'DELETED' WHERE id = ?")
@SQLRestriction("status != 'DELETED'")
@NoArgsConstructor(access = PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255, unique = true)
    private String slug;

    @Column(length = 500)
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String fullDescription;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @JoinColumn(name = "seller_id")
    @ManyToOne(fetch = LAZY)
    private Seller seller;

    @JoinColumn(name = "brand_id")
    @ManyToOne(fetch = LAZY)
    private Brand brand;

    @OneToOne(mappedBy = "product", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductDetail productDetail;

    @OneToOne(mappedBy = "product", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductPrice productPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductStatus status;

    @OneToMany(mappedBy = "product",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOptionGroup> productOptionGroups = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCategory> productCategory = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<ProductTag> productTags = new ArrayList<>();

    @Builder
    public Product(Long id,
                   String name,
                   String slug,
                   String shortDescription,
                   String fullDescription,
                   Seller seller,
                   Brand brand,
                   ProductStatus status,
                   ProductDetail productDetail,
                   ProductPrice productPrice,
                   List<ProductImage> productImages,
                   List<ProductOptionGroup> productOptionGroups
    ) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.seller = seller;
        this.brand = brand;
        this.status = status;

        setProductDetail(productDetail);
        setProductPrice(productPrice);
        addProductImages(productImages);
        addProductOptionGroups(productOptionGroups);
    }

    public void changeProduct(Long id,
                   String name,
                   String slug,
                   String shortDescription,
                   String fullDescription,
                   Seller seller,
                   Brand brand,
                   ProductStatus status,
                   ProductDetail productDetail,
                   ProductPrice productPrice
    ) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.seller = seller;
        this.brand = brand;
        this.status = status;

        setProductDetail(productDetail);
        setProductPrice(productPrice);
    }

    public void clearProductSeries() {
        clearProductOptions();
        clearProductImage();
        clearCategories();
        clearTags();
    }


    public void addProductOptionGroups(List<ProductOptionGroup> productOptionGroups) {
        for (ProductOptionGroup productOptionGroup : productOptionGroups) {
            productOptionGroup.setProduct(this);
            this.getProductOptionGroups().add(productOptionGroup);
        }
    }

    public void addProductImages(List<ProductImage> productImages) {
        for (ProductImage productImage : productImages) {
            productImage.setProduct(this);
            this.productImages.add(productImage);
        }
    }

    public void addProductCategories(List<ProductCategory> categories) {
        for (ProductCategory productCategory : categories) {
            productCategory.setProduct(this);
            this.productCategory.add(productCategory);
        }
    }

    public void addProductTags(List<ProductTag> tags) {
        for (ProductTag productTag : tags) {
            productTag.setProduct(this);
            this.productTags.add(productTag);
        }
    }

    private void setProductPrice(ProductPrice productPrice) {
        productPrice.setProduct(this);
        this.productPrice = productPrice;
    }

    private void setProductDetail(ProductDetail productDetail) {
        productDetail.setProduct(this);
        this.productDetail = productDetail;
    }

    private void clearProductOptions() {
        this.productOptionGroups.forEach(ProductOptionGroup::clearOption);
        this.productOptionGroups.clear();
    }

    private void clearProductImage() {
        this.productImages.clear();
    }

    private void clearCategories() {
        this.productCategory.clear();
    }

    private void clearTags() {
        this.productTags.clear();
    }
}