package kr.co.wanted.backend31.controller.vo;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import kr.co.wanted.backend31.common.model.product.specification.ProductOptionCreateSpecification;

public record ProductOptionCreateRequest(@NotBlank String name, @NotNull BigDecimal additionalPrice,
                @NotBlank String sku,
                @PositiveOrZero int stock, @Positive int displayOrder) {

        public ProductOptionCreateSpecification to() {
                return ProductOptionCreateSpecification.builder()
                                .name(name)
                                .additionalPrice(additionalPrice)
                                .sku(sku)
                                .stock(stock)
                                .displayOrder(displayOrder)
                                .build();
        }

}
