package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.ProductOption;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Schema(description = "옵션 정보")
public class FindProductOptionDTO {

    @Schema(description = "옵션 식별 번호")
    private Long id;

    @Schema(description = "옵션 이름")
    private String name;

    @Schema(description = "추가 가격")
    private BigDecimal additionalPrice;

    @Schema(description = "SKU 코드")
    private String sku;

    @Schema(description = "재고 수량")
    private Integer stock;

    @Schema(description = "표시 순서")
    private Integer displayOrder;

    public static FindProductOptionDTO from(ProductOption productOption) {
        FindProductOptionDTO findProductOptionDTO = new FindProductOptionDTO();
        findProductOptionDTO.id = productOption.getId();
        findProductOptionDTO.name = productOption.getName();
        findProductOptionDTO.additionalPrice = productOption.getAdditionalPrice();
        findProductOptionDTO.sku = productOption.getSku();
        findProductOptionDTO.stock = productOption.getStock();
        findProductOptionDTO.displayOrder = productOption.getDisplayOrder();
        return findProductOptionDTO;
    }
}
