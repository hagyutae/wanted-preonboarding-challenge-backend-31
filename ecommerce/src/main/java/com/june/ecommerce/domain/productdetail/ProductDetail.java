package com.june.ecommerce.domain.productdetail;

import com.june.ecommerce.domain.product.Product;
import jakarta.persistence.*;

@Entity
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Double weight;
    private String dimensions;
    private String materials;
    private String countryOfOrigin;
    private String warrantyInfo;
    private String careInstructions;
    private String additionalInfo;

}
