package com.june.ecommerce.dto.product;

import com.june.ecommerce.domain.producttag.ProductTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductTagDto {
    private int tagId;

    public static ProductTagDto fromEntity(ProductTag entity) {
        return ProductTagDto.builder()
                .tagId(entity.getTag().getId())
                .build();
    }
}
