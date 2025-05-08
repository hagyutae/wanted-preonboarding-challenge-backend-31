package com.june.ecommerce.domain.product;

import com.june.ecommerce.domain.seller.Seller;
import com.june.ecommerce.domain.brand.Brand;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;

    private LocalDateTime createAt = LocalDateTime.now();
    private LocalDateTime updateAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private String status;

    public void update(String name, String slug, String shortDescription, String fullDescription, Seller seller, Brand brand, String status) {
        Optional.ofNullable(name).ifPresent(value -> this.name = value);
        Optional.ofNullable(slug).ifPresent(value -> this.slug = value);
        Optional.ofNullable(shortDescription).ifPresent(value -> this.shortDescription = value);
        Optional.ofNullable(fullDescription).ifPresent(value -> this.fullDescription = value);
        Optional.ofNullable(seller).ifPresent(value -> this.seller = value);
        Optional.ofNullable(brand).ifPresent(value -> this.brand = value);
        Optional.ofNullable(status).ifPresent(value -> this.status = value);
    }
}
