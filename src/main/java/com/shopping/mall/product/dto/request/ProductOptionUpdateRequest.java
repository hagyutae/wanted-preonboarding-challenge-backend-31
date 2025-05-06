package com.shopping.mall.product.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOptionUpdateRequest {

    private String name;
    private Integer additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;
}