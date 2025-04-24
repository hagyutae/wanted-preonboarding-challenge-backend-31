package com.wanted.ecommerce.product.domain;

import com.wanted.ecommerce.brand.domain.Brand;
import com.wanted.ecommerce.common.domain.BaseEntity;
import com.wanted.ecommerce.seller.domain.Seller;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true, nullable = false)
    private String slug;
    private String shortDescription;
    private String fullDescription;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @OneToMany(mappedBy = "product")
    private List<ProductCategory> categories;

    @OneToMany(mappedBy = "product")
    private List<ProductDetail> details;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> images;

    @OneToMany(mappedBy = "product")
    private List<ProductPrice> prices;

    @OneToMany(mappedBy = "product")
    private List<ProductOptionGroup> optionGroups;

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public static Product of(String name, String slug, String shortDescription, String fullDescription, Seller seller, Brand brand, ProductStatus status){
        return Product.builder()
            .name(name)
            .slug(slug)
            .shortDescription(shortDescription)
            .fullDescription(fullDescription)
            .seller(seller)
            .brand(brand)
            .status(status)
            .build();
    }
}
