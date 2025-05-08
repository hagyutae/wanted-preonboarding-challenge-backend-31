package com.june.ecommerce.domain.productoption;

import com.june.ecommerce.domain.productoptiongroup.ProductOptionGroup;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup group;

    private String name;
    private int additionalPrice = 0;
    private String sku;
    private int stock = 0;
    private int displayOrder = 0;

    @Builder
    public ProductOption(int id, ProductOptionGroup group, String name, int additionalPrice, String sku, int stock, int displayOrder) {
        this.id = id;
        this.group = group;
        this.name = name;
        this.additionalPrice = additionalPrice;
        this.sku = sku;
        this.stock = stock;
        this.displayOrder = displayOrder;
    }
}
