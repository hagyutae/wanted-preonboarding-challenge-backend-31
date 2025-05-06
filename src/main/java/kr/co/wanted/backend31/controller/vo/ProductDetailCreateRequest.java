package kr.co.wanted.backend31.controller.vo;

import java.math.BigDecimal;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import kr.co.wanted.backend31.common.model.product.specification.ProductDetailCreateSpecification;

public record ProductDetailCreateRequest(@PositiveOrZero BigDecimal weight,
		@NotNull Map<String, Object> dimensions,
		@NotBlank String materials, @NotBlank String countryOfOrigin, @NotBlank String warrantyInfo,
		@NotBlank String careInstructions, @NotNull Map<String, Object> additionalInfo) {

	public ProductDetailCreateSpecification to() {
		return ProductDetailCreateSpecification.builder()
				.weight(weight)
				.dimensions(dimensions)
				.materials(materials)
				.countryOfOrigin(countryOfOrigin)
				.warrantyInfo(warrantyInfo)
				.careInstructions(careInstructions)
				.additionalInfo(additionalInfo)
				.build();
	}

}
