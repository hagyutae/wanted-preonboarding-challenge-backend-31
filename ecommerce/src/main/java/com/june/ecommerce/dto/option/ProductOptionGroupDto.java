package com.june.ecommerce.dto.option;

import com.june.ecommerce.domain.productoption.ProductOption;
import com.june.ecommerce.domain.productoptiongroup.ProductOptionGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionGroupDto {
    private int id;
    private String name;
    private int displayOrder;
    private List<ProductOptionDto> options;

    public static ProductOptionGroupDto fromEntity(ProductOptionGroup entity, List<ProductOption> options) {
        return ProductOptionGroupDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .displayOrder(entity.getDisplayOrder())
                .options(
                        options.stream()
                                .map(ProductOptionDto::fromEntity)
                                .collect(Collectors.toList())
                )
                .build();
    }
}


