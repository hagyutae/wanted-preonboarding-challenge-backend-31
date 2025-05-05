package com.dino.cqrs_challenge.presentation.model.dto;

import com.dino.cqrs_challenge.domain.entity.ProductPrice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Schema(description = "가격 정보")
public class FindProductPriceDTO {

    @Schema(description = "기본 가격")
    private BigDecimal basePrice;

    @Schema(description = "할인가")
    private BigDecimal salePrice;

    @Schema(description = "통화")
    private String currency;

    @Schema(description = "세율(%)")
    private BigDecimal taxRate;

    @Schema(description = "할인율")
    private Integer discountPercentage;

    public static FindProductPriceDTO from(ProductPrice productPrice) {
        FindProductPriceDTO findProductPriceDTO = new FindProductPriceDTO();
        findProductPriceDTO.basePrice = productPrice.getBasePrice();
        findProductPriceDTO.salePrice = productPrice.getSalePrice();
        findProductPriceDTO.currency = productPrice.getCurrency();
        findProductPriceDTO.taxRate = productPrice.getTaxRate();
        // 할인율 계산 로직 추가
        BigDecimal base = productPrice.getBasePrice();
        BigDecimal sale = productPrice.getSalePrice();

        if (base != null && sale != null && base.compareTo(BigDecimal.ZERO) > 0 && sale.compareTo(base) < 0) {
            BigDecimal discountRate = base.subtract(sale)
                    .divide(base, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            findProductPriceDTO.discountPercentage = discountRate.setScale(0, RoundingMode.DOWN).intValue(); // 정수 할인율
        } else {
            findProductPriceDTO.discountPercentage = 0; // 할인 없음
        }
        return findProductPriceDTO;
    }
}
