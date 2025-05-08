package com.pawn.wantedcqrs.productOptionGroup.dto;

import com.pawn.wantedcqrs.productOptionGroup.entity.ProductOption;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductOptionDto {

    private Long id;

    private Long optionGroupId;

    private String name;

    private BigDecimal additionalPrice;

    private String sku;

    private Integer stock;

    private Integer displayOrder;

    public ProductOption toEntity() {
        return ProductOption.builder()
                .id(id)
//                .optionGroup(optionGroupId)
                .name(name)
                .additionalPrice(additionalPrice)
                .sku(sku)
                .stock(stock)
                .displayOrder(displayOrder)
                .build();
    }

    public static ProductOptionDto fromEntity(ProductOption option) {
        ProductOptionDto dto = new ProductOptionDto();
        dto.setId(option.getId());
        dto.setOptionGroupId(
                option.getOptionGroup() != null ? option.getOptionGroup().getId() : null
        );
        dto.setName(option.getName());
        dto.setAdditionalPrice(option.getAdditionalPrice());
        dto.setSku(option.getSku());
        dto.setStock(option.getStock());
        dto.setDisplayOrder(option.getDisplayOrder());

        return dto;
    }

}
