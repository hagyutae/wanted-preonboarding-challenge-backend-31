package com.example.cqrsapp.product.dto.response;

import com.example.cqrsapp.product.domain.ProductImage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterProductImageResponseDto {

    private Long id;
    private String url;
    private String altText;
    private Boolean isPrimary;
    private Integer displayOrder;
    private Long optionId;

    public static RegisterProductImageResponseDto fromEntity(ProductImage productImage) {
        return RegisterProductImageResponseDto.builder()
                .id(productImage.getId())
                .url(productImage.getUrl())
                .altText(productImage.getAltText())
                .isPrimary(productImage.getIsPrimary())
                .displayOrder(productImage.getDisplayOrder())
                .optionId(productImage.getOptionId())
                .build();
    }
}
