package com.june.ecommerce.dto.category;

import com.june.ecommerce.domain.productcategory.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDto {
    private int categoryId;
    private boolean isPrimary;

    public static ProductCategoryDto fromEntity(ProductCategory entity) {
        return ProductCategoryDto.builder()
                .categoryId(entity.getCategory().getId())
                .isPrimary(entity.getIsPrimary())
                .build();
    }
}
