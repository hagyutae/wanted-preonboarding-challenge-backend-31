package com.sandro.wanted_shop.product.entity;

import com.sandro.wanted_shop.brand.Brand;
import com.sandro.wanted_shop.category.Category;
import com.sandro.wanted_shop.common.entity.BaseTimeEntity;
import com.sandro.wanted_shop.product.dto.CreateProductCommand;
import com.sandro.wanted_shop.product.dto.UpdateProductCommand;
import com.sandro.wanted_shop.product.entity.enums.ProductStatus;
import com.sandro.wanted_shop.product.entity.relation.ProductCategory;
import com.sandro.wanted_shop.product.entity.relation.ProductOptionGroup;
import com.sandro.wanted_shop.product.entity.relation.ProductTag;
import com.sandro.wanted_shop.review.entity.Review;
import com.sandro.wanted_shop.seller.Seller;
import com.sandro.wanted_shop.tag.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.*;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String fullDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductPrice price;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductDetail detail;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCategory> categories;

    @OrderBy("displayOrder")
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOptionGroup> optionGroups;

    @OrderBy("displayOrder")
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductTag> tags;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @Builder
    public Product(String name, String slug, String shortDescription, String fullDescription,
                   Seller seller, Brand brand, ProductStatus status,
                   List<ProductCategory> categories,
                   List<ProductOptionGroup> optionGroups,
                   List<ProductImage> images, List<ProductTag> tags, List<Review> reviews
    ) {
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.seller = seller;
        this.brand = brand;
        this.status = status;
        this.categories = Optional.ofNullable(categories).orElse(new ArrayList<>());
        this.optionGroups = Optional.ofNullable(optionGroups).orElse(new ArrayList<>());
        this.images = Optional.ofNullable(images).orElse(new ArrayList<>());
        this.tags = Optional.ofNullable(tags).orElse(new ArrayList<>());
        this.reviews = Optional.ofNullable(reviews).orElse(new ArrayList<>());

        validate();

        if (this.seller != null)
            this.seller.addProduct(this);

        if (this.brand != null)
            this.brand.addProduct(this);
    }

    void validate() {
        assert StringUtils.hasText(this.name)
                && StringUtils.hasText(this.slug)
                && this.status != null
                : "Product name, slug, status must be present";
    }

    public void updatePrice(ProductPrice productPrice) {
        this.price = productPrice;
    }

    public void updateDetail(ProductDetail productDetail) {
        this.detail = productDetail;
    }

    public void addCategory(Category category) {
        this.addCategory(category, false);
    }

    public void addCategory(Category category, boolean isPrimary) {
        this.categories.add(ProductCategory.of(this, category, isPrimary));
    }

    public void addTag(Tag tag) {
        this.tags.add(ProductTag.of(this, tag));
    }

    public void addOptionGroup(ProductOptionGroup optionGroup) {
        this.optionGroups.add(optionGroup);
    }

    public void addImage(ProductImage productImage) {
        this.images.add(productImage);
    }

    // TODO: 고도화하기
    public void update(UpdateProductCommand command) {
        this.name = command.name();
        this.slug = command.slug();
        this.shortDescription = command.shortDescription();
        this.fullDescription = command.fullDescription();
        this.status = command.status();
    }

    public void addAllTags(List<Tag> tags) {
        List<ProductTag> productTags = tags.stream()
                .map(tag -> ProductTag.of(this, tag))
                .toList();
        this.tags.addAll(productTags);
    }

    public void addAllCategories(List<CreateProductCommand.Category> categoryCommands, Map<Long, Category> categoryMap) {
        List<ProductCategory> productCategories = categoryCommands.stream()
                .map(command -> ProductCategory.of(this, categoryMap.get(command.id())))
                .toList();
        this.categories.addAll(productCategories);
    }

    // ... 나머지 필드 및 관계 매핑
} 