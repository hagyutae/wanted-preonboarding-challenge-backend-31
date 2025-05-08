package com.example.demo.product.entity;

import com.example.demo.brand.entity.BrandEntity;
import com.example.demo.product.controller.request.CreateProductRequest;
import com.example.demo.product.controller.request.UpdateProductRequest;
import com.example.demo.user.entity.SellerEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", columnDefinition = "VARCHAR(255)", nullable = false)
    private String name;
    @Column(name = "slug", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String slug;
    @Column(name = "short_description", columnDefinition = "VARCHAR(500)")
    private String shortDescription;
    @Column(name = "full_description", columnDefinition = "TEXT")
    private String fullDescription;
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private SellerEntity sellerEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private BrandEntity brandEntity;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(20)", nullable = false)
    private ProductStatus status;
    @OneToOne(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private ProductPriceEntity productPriceEntity;
    @OneToOne(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private ProductDetailEntity productDetailEntity;

    public static ProductEntity create(
            CreateProductRequest request,
            SellerEntity sellerEntity,
            BrandEntity brandEntity
    ) {
        ProductPriceEntity productPriceEntity = ProductPriceEntity.create(request.price());
        ProductDetailEntity productDetailEntity = ProductDetailEntity.create(request.detail());

        ProductEntity productEntity = new ProductEntity(
                null,
                request.name(),
                request.slug(),
                request.shortDescription(),
                request.fullDescription(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                sellerEntity,
                brandEntity,
                ProductStatus.valueOf(request.status()),
                productPriceEntity,
                productDetailEntity
        );

        productPriceEntity.connectProductEntity(productEntity);
        productDetailEntity.connectProductEntity(productEntity);

        return productEntity;
    }

    public void updateSeller(SellerEntity sellerEntity) {
        this.sellerEntity = sellerEntity;
    }

    public void updateBrand(BrandEntity brandEntity) {
        this.brandEntity = brandEntity;

    }

    public void update(UpdateProductRequest request) {
        this.productPriceEntity.update(request.price());
        this.productDetailEntity.update(request.detail());
        this.name = request.name();
        this.slug = request.slug();
        this.shortDescription = request.shortDescription();
        this.fullDescription = request.fullDescription();
        this.status = ProductStatus.valueOf(request.status());
        this.updatedAt = LocalDateTime.now();
    }

    public void softDelete() {
        this.status = ProductStatus.DELETED;
        this.updatedAt = LocalDateTime.now();
    }
}
