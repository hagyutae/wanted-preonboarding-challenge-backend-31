package com.june.ecommerce.domain.productoption;

import com.june.ecommerce.domain.productoptiongroup.ProductOptionGroup;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "option_group_id")
    private ProductOptionGroup group;

    private String name;
    private BigDecimal additionalPrice = BigDecimal.ZERO;
    private String sku;
    private int stock = 0;
    private int displayOrder = 0;
}
