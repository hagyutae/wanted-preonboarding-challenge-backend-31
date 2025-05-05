package com.june.ecommerce.domain.product;

import com.june.ecommerce.domain.seller.Seller;
import com.june.ecommerce.domain.brand.Brand;
import com.june.ecommerce.dto.product.ProductRequestDto;
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

    public void updateProduct(ProductRequestDto requestDto) {
        Optional.ofNullable(requestDto.getName()).ifPresent(name -> this.name = name);
        Optional.ofNullable(requestDto.getSlug()).ifPresent(slug -> this.slug = slug);
        Optional.ofNullable(requestDto.getShortDescription()).ifPresent(shortDescription -> this.shortDescription = shortDescription);
        Optional.ofNullable(requestDto.getFullDescription()).ifPresent(fullDescription -> this.fullDescription = fullDescription);
    }

}
