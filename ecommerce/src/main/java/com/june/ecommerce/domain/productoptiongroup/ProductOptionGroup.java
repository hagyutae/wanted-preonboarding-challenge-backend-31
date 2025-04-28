package com.june.ecommerce.domain.productoptiongroup;

import com.june.ecommerce.domain.product.Product;
import jakarta.persistence.*;

@Entity
public class ProductOptionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;
    private int displayOrder = 0;
}
