package com.wanted_preonboarding_challenge_backend.eCommerce.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Product extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    private String shortDescription;
    @Column(columnDefinition = "TEXT")
    private String fullDescription;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private ProductDetail detail;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private ProductPrice price;


    public void deleteProduct(){
        this.status = "DELETED";
    }

    public void addProductDetail(ProductDetail productDetail){
        this.detail = productDetail;
    }

    public void addProductPrice(ProductPrice productPrice){
        this.price = productPrice;
    }

    public void addSeller(Seller seller){
        this.seller = seller;
    }

    public void addBrand( Brand brand){
        this.brand = brand;
    }

    public void update(String name, String slug, String shortDesc, String fullDesc, String status) {
        this.name = name;
        this.slug = slug;
        this.shortDescription = shortDesc;
        this.fullDescription = fullDesc;
        this.status = status;
    }

    public void updateSeller(Seller seller){
        this.seller = seller;
    }

    public void updateBrand(Brand brand){
        this.brand = brand;
    }


}