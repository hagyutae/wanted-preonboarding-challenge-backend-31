package com.example.cqrsapp.product.dto.response;

import com.example.cqrsapp.product.domain.ProductOption;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterProductOptionResponseDto {
    private Long id;
    private String name;
    private Integer additionalPrice;
    private String sku;
    private Integer stock;
    private Integer displayOrder;

    public static RegisterProductOptionResponseDto fromEntity(ProductOption productOption) {
        return RegisterProductOptionResponseDto.builder()
                .id(productOption.getId())
                .name(productOption.getName())
                .additionalPrice(productOption.getAdditionalPrice().intValue())
                .sku(productOption.getSku())
                .stock(productOption.getStock())
                .displayOrder(productOption.getDisplayOrder())
                .build();
    }
}
