package com.june.ecommerce.domain.productdetail;

import com.june.ecommerce.domain.product.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Entity
@Getter
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Double weight;

    @Column(columnDefinition = "jsonb")
    private String dimensions;

    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;

    @Column(columnDefinition = "jsonb")
    private String additionalInfo;

    @Builder
    public ProductDetail(int id, Product product, Double weight, String dimensions, String materials, String countryOfOrigin, String warrantyInfo, String careInstructions, String additionalInfo) {
        this.id = id;
        this.product = product;
        this.weight = weight;
        this.dimensions = dimensions;
        this.materials = materials;
        this.countryOfOrigin = countryOfOrigin;
        this.warrantyInfo = warrantyInfo;
        this.careInstructions = careInstructions;
        this.additionalInfo = additionalInfo;
    }

    public void update(double weight, String dimensions, String materials, String countryOfOrigin, String warrantyInfo, String careInstructions, String additionalInfo) {
        Optional.ofNullable(weight).ifPresent(w -> this.weight = w);
        Optional.ofNullable(dimensions).ifPresent(d -> this.dimensions = d);
        Optional.ofNullable(materials).ifPresent(m -> this.materials = m);
        Optional.ofNullable(countryOfOrigin).ifPresent(o -> this.countryOfOrigin = o);
        Optional.ofNullable(warrantyInfo).ifPresent(w -> this.warrantyInfo = w);
        Optional.ofNullable(careInstructions).ifPresent(c -> this.careInstructions = c);
        Optional.ofNullable(additionalInfo).ifPresent(o -> this.additionalInfo = o);
    }
}
