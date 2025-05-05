package com.june.ecommerce.domain.producttag;

import com.june.ecommerce.domain.product.Product;
import com.june.ecommerce.domain.tag.Tag;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ProductTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
