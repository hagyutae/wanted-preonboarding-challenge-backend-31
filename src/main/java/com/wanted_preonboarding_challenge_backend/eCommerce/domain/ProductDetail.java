package com.wanted_preonboarding_challenge_backend.eCommerce.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "product_detail")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Double weight;

    @Column(columnDefinition = "JSON")
    private String dimensions;
    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;
    @Column(columnDefinition = "JSONB")
    private String additionalInfo;

    public void addProduct(Product product){
        this.product = product;

        if(product.getDetail() != this){
            product.addProductDetail(this);
        }
    }

    public void update(Double weight, String dimensions, String materials, String countryOfOrigin, String warrantyInfo, String careInstructions, String additionalInfo) {
        this.weight = weight;
        this.dimensions = dimensions;
        this.materials = materials;
        this.countryOfOrigin = countryOfOrigin;
        this.warrantyInfo = warrantyInfo;
        this.careInstructions = careInstructions;
        this.additionalInfo = additionalInfo;
    }
}