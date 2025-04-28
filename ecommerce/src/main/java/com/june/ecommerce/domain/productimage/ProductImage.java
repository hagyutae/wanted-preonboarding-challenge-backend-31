package com.june.ecommerce.domain.productimage;

import com.june.ecommerce.domain.product.Product;
import com.june.ecommerce.domain.productoption.ProductOption;
import jakarta.persistence.*;

@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String url;
    private String altText;
    private Boolean isPrimary = false;
    private int displayOrder = 0;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private ProductOption productOption;
}
