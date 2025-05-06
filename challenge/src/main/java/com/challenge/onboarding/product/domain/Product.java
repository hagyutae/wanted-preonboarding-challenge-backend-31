package com.challenge.onboarding.product.domain;

import com.challenge.onboarding.product.service.dto.request.CreateProductRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(name = "short_description")
    private String shortDescription;

    @Lob
    @Column(name = "full_description")
    private String fullDescription;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public enum Status {
        AVAILABLE, SOLD_OUT, DELETED
    }

    public static Product from(CreateProductRequest createProductRequest, Seller seller, Brand brand) {
        Product product = new Product();
        product.registerProduct(createProductRequest, seller, brand);
        return product;
    }

    public void registerProduct(CreateProductRequest createProductRequest, Seller seller, Brand brand) {
        this.name = createProductRequest.name();
        this.slug = createProductRequest.slug();
        this.shortDescription = createProductRequest.shortDescription();
        this.fullDescription = createProductRequest.fullDescription();
        this.status = Status.AVAILABLE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.seller = seller;
        this.brand = brand;
    }

}
