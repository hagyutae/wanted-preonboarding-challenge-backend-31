package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.Category;
import com.dino.cqrs_challenge.domain.entity.Product;
import com.dino.cqrs_challenge.domain.entity.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "카테고리 정보")
public class SaveProductCategoryDTO {

    @Schema(description = "카테고리 ID")
    private Long categoryId;

    @Schema(description = "대표 카테고리 여부")
    private Boolean isPrimary;

    public ProductCategory toEntity(Product product, Category category) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setIsPrimary(isPrimary);
        productCategory.setCategory(category);
        productCategory.setProduct(product);
        return productCategory;
    }
}
