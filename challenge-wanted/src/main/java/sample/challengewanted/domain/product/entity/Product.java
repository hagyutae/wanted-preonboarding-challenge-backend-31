package sample.challengewanted.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.challengewanted.api.controller.product.request.ProductCreateRequest;
import sample.challengewanted.domain.BaseEntity;
import sample.challengewanted.domain.brand.Brand;
import sample.challengewanted.domain.category.ProductCategory;
import sample.challengewanted.domain.seller.Seller;
import sample.challengewanted.domain.tag.ProductTag;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductCategory> productCategories = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductTag> productTags = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductOptionGroup> productOptionGroups = new ArrayList<>();

    @OneToOne(mappedBy = "product")
    private ProductPrice price;

    private Product(ProductCreateRequest request, Seller seller, Brand brand) {
        this.name = request.getName();
        this.slug = request.getSlug();
        this.shortDescription = request.getShortDescription();
        this.fullDescription = request.getFullDescription();
        this.status = request.getStatus();
        this.seller = seller;
        this.brand = brand;
    }

    public static Product create(ProductCreateRequest request, Seller seller, Brand brand) {
        return new Product(request, seller, brand);
    }

    public void assignSeller(Seller seller) {
        this.seller = seller;
    }

    public void assignBrand(Brand brand) {
        this.brand = brand;
    }

    public void addProductCategory(ProductCategory productCategory) {
        this.productCategories.add(productCategory);
        productCategory.associateProduct(this);
    }

    public void addProductTag(ProductTag productTag) {
        this.productTags.add(productTag);
        productTag.assignProduct(this);
    }

    public void addProductOptionGroup(ProductOptionGroup productOptionGroup) {
        this.productOptionGroups.add(productOptionGroup);
        productOptionGroup.assignProduct(this);
    }

}
