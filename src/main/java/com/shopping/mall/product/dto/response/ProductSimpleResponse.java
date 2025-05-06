package com.shopping.mall.product.dto.response;

import com.shopping.mall.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductSimpleResponse {

    private Long id;
    private String name;
    private String slug;
    private String shortDescription;

    public static ProductSimpleResponse from(Product product) {
        return new ProductSimpleResponse(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getShortDescription()
        );
    }
}
