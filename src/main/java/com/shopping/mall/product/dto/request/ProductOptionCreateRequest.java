package com.shopping.mall.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductOptionCreateRequest {

    private String name;
    private Integer displayOrder;
    private List<OptionRequest> options;

    @Getter
    @NoArgsConstructor
    public static class OptionRequest {
        private String name;
        private Integer additionalPrice;
        private String sku;
        private Integer stock;
        private Integer displayOrder;
    }
}
