package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.ProductOption;
import com.dino.cqrs_challenge.domain.entity.ProductOptionGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Schema(description = "상품 옵션")
public class SaveProductOptionDTO {

    @Schema(description = "옵션명")
    private String name;

    @Schema(description = "추가 금액")
    private BigDecimal additionalPrice;

    @Schema(description = "SKU 코드")
    private String sku;

    @Schema(description = "재고 수량")
    private Integer stock;

    @Schema(description = "표시 순서")
    private Integer displayOrder;

    public ProductOption toEntity(ProductOptionGroup productOptionGroup) {
        ProductOption productOption = new ProductOption();
        productOption.setName(name);
        productOption.setAdditionalPrice(additionalPrice);
        productOption.setSku(sku);
        productOption.setStock(stock);
        productOption.setDisplayOrder(displayOrder);
        productOption.setProductOptionGroup(productOptionGroup);
        return productOption;
    }
}
