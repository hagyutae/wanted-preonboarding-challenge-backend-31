package com.june.ecommerce.domain.product;

import com.june.ecommerce.domain.seller.Seller;
import com.june.ecommerce.domain.brand.Brand;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
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

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private String status;
}
