package com.june.ecommerce.domain.productimage;

import com.june.ecommerce.domain.product.Product;
import com.june.ecommerce.domain.productoption.ProductOption;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
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

    @Builder
    public ProductImage(int id, Product product, String url, String altText, Boolean isPrimary, int displayOrder, ProductOption productOption) {
        this.id = id;
        this.product = product;
        this.url = url;
        this.altText = altText;
        this.isPrimary = isPrimary;
        this.displayOrder = displayOrder;
        this.productOption = productOption;
    }
}
