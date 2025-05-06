package com.june.ecommerce.domain.productoptiongroup;

import com.june.ecommerce.domain.product.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
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

    @Builder
    public ProductOptionGroup(int id, Product product, String name, int displayOrder) {
        this.id = id;
        this.product = product;
        this.name = name;
        this.displayOrder = displayOrder;
    }
}
