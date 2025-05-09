package com.wanted_preonboarding_challenge_backend.eCommerce.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_price")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Double basePrice;
    private Double salePrice;
    private Double costPrice;
    private String currency;
    private Double taxRate;

    public void addProduct(Product product){
        this.product = product;

        if(product.getPrice() != this){
            product.addProductPrice(this);
        }
    }

    public void update(Double basePrice, Double salePrice, Double costPrice, String currency, Double taxRate) {
        this.basePrice = basePrice;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.currency = currency;
        this.taxRate = taxRate;
    }
}