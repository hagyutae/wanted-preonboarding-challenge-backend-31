package com.wanted.ecommerce.product.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record ProductOptionGroupResponse(
    Long id,
    String name,
    Integer displayOrder,
    List<ProductOptionResponse> options
) {
    public static ProductOptionGroupResponse of (Long id, String name, Integer displayOrder, List<ProductOptionResponse> options){
        return ProductOptionGroupResponse.builder()
            .id(id)
            .name(name)
            .displayOrder(displayOrder)
            .options(options)
            .build();
    }
}
