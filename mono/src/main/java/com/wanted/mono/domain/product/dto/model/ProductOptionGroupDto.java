package com.wanted.mono.domain.product.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanted.mono.domain.product.entity.ProductOptionGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductOptionGroupDto {
    private Long id;
    private String name;
    @JsonProperty("display_order")
    private Integer displayOrder;
    private List<ProductOptionDto> options;

    // ----------------

    public static ProductOptionGroupDto of(ProductOptionGroup productOptionGroup, List<ProductOptionDto> optionDtos) {
        ProductOptionGroupDto dto = new ProductOptionGroupDto();
        dto.id = productOptionGroup.getId();
        dto.name = productOptionGroup.getName();
        dto.displayOrder = productOptionGroup.getDisplayOrder();
        dto.options = optionDtos;
        return dto;
    }
}
