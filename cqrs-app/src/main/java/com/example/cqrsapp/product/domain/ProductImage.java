package com.example.cqrsapp.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "product_images")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 255)
    private String url;

    @Column(nullable = false, length = 255)
    private String altText;

    private Boolean isPrimary;

    private Integer displayOrder;

    private Long optionId;

    @Builder
    public ProductImage(Product product, String url, String altText, Boolean isPrimary, Integer displayOrder, Long optionId) {
        this.product = product;
        this.url = url;
        this.altText = altText;
        this.isPrimary = isPrimary;
        this.displayOrder = displayOrder;
        this.optionId = optionId;
    }
}