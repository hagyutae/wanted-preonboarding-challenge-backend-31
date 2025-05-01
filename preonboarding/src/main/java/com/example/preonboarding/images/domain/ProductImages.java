package com.example.preonboarding.images.domain;

import com.example.preonboarding.options.domain.ProductOption;
import com.example.preonboarding.products.domain.Products;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_images")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String altText;
    private boolean isPrimary;
    private int displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private ProductOption options;

    public void setProducts(Products products) {
        this.products = products;
    }
}
