package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.detail;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record QueryProductPriceDTO(
    BigDecimal base_price,
    BigDecimal sale_price,
    String currency,
    BigDecimal tax_rate
//    Double  discount_percentage
) {
}
