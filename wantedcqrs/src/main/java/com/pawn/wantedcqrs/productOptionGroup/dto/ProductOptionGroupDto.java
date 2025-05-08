package com.pawn.wantedcqrs.productOptionGroup.dto;

import com.pawn.wantedcqrs.productOptionGroup.entity.ProductOptionGroup;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductOptionGroupDto {

    private Long id;

    private Long productId;

    private String name;

    private int displayOrder;

    private List<ProductOptionDto> options = new ArrayList<>();

//    public ProductOptionGroup toEntity() {
//        return ProductOptionGroup.builder()
//                .id(id)
//                .productId(productId)
//                .name(name)
//                .displayOrder(displayOrder)
//                .options(options.stream()
//                        .map(ProductOptionDto::toEntity)
//                        .collect(Collectors.toList())
//                ).build();
//    }

    public static ProductOptionGroupDto fromEntity(ProductOptionGroup entity) {
        ProductOptionGroupDto dto = new ProductOptionGroupDto();
        dto.setId(entity.getId());
        dto.setProductId(entity.getProductId());
        dto.setName(entity.getName());
        dto.setDisplayOrder(entity.getDisplayOrder());

        if (entity.getOptions() != null) {
            List<ProductOptionDto> optionDtos = entity.getOptions().stream()
                    .map(ProductOptionDto::fromEntity)
                    .collect(Collectors.toList());
            dto.setOptions(optionDtos);
        }

        return dto;
    }

}
