package kr.co.wanted.backend31.controller.vo;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import kr.co.wanted.backend31.common.model.product.specification.ProductPriceCreateSpecification;

public record ProductPriceCreateRequest(@PositiveOrZero BigDecimal basePrice, @PositiveOrZero BigDecimal salePrice,
		@PositiveOrZero BigDecimal costPrice, @NotBlank String currency, @PositiveOrZero BigDecimal taxRate) {

	public ProductPriceCreateSpecification to() {
		return ProductPriceCreateSpecification.builder()
				.basePrice(basePrice)
				.salePrice(salePrice)
				.costPrice(costPrice)
				.currency(currency)
				.taxRate(taxRate)
				.build();
	}

}
