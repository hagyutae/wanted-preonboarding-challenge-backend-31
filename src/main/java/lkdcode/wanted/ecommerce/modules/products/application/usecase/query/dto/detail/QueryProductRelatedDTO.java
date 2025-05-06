package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.detail;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record QueryProductRelatedDTO(
    Long id,
    String name,
    String slug,
    String short_description,
    Images primary_image,
    BigDecimal base_price,
    BigDecimal sale_price,
    String currency
) {

    @Builder
    public record Images(
        String url,
        String alt_text
    ) {
    }
}