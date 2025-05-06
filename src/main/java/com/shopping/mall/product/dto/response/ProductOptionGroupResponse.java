package com.shopping.mall.product.dto.response;

import com.shopping.mall.product.entity.ProductOptionGroup;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ProductOptionGroupResponse {

    private String name;
    private Integer displayOrder;
    private List<OptionResponse> options;

    @Getter
    @Builder
    public static class OptionResponse {
        private String name;
        private Integer additionalPrice;
        private String sku;
        private Integer stock;
        private Integer displayOrder;
    }

    public static ProductOptionGroupResponse from(ProductOptionGroup group) {
        return ProductOptionGroupResponse.builder()
                .name(group.getName())
                .displayOrder(group.getDisplayOrder())
                .options(group.getOptions().stream().map(option ->
                        OptionResponse.builder()
                                .name(option.getName())
                                .additionalPrice(option.getAdditionalPrice())
                                .sku(option.getSku())
                                .stock(option.getStock())
                                .displayOrder(option.getDisplayOrder())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }
}
