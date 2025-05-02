package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.Product;
import com.dino.cqrs_challenge.domain.entity.ProductPrice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Schema(description = "가격 정보")
public class SaveProductPriceDTO {

    @Schema(description = "기본 가격")
    private BigDecimal basePrice;

    @Schema(description = "할인가")
    private BigDecimal salePrice;

    @Schema(description = "원가")
    private BigDecimal costPrice;

    @Schema(description = "통화")
    private String currency;

    @Schema(description = "세율(%)")
    private Integer taxRate;

    public ProductPrice toEntity(Product product) {
        ProductPrice productPrice = new ProductPrice();
        productPrice.setProduct(product);
        productPrice.setBasePrice(basePrice);
        productPrice.setSalePrice(salePrice);
        productPrice.setCostPrice(costPrice);
        productPrice.setCurrency(currency);
        return productPrice;
    }
}
