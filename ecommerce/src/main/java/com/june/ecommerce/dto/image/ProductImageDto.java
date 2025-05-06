package com.june.ecommerce.dto.image;

import com.june.ecommerce.domain.productimage.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDto {

    private int id;
    private String url;
    private String altTetx;
    private boolean isPrimary;
    private int displayOrder;
    private int optionId;

    public static ProductImageDto fromEntity(ProductImage entity) {
        return ProductImageDto.builder()
                .id(entity.getId())
                .url(entity.getUrl())
                .altTetx(entity.getAltText())
                .isPrimary(entity.getIsPrimary())
                .displayOrder(entity.getDisplayOrder())
                .optionId(entity.getProductOption().getId())
                .build();
    }
}
