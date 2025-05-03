package com.example.preonboarding.tags.domain;

import com.example.preonboarding.products.domain.Products;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_tags")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tags tags;

    public void setProducts(Products products) {
        this.products = products;
    }
}
