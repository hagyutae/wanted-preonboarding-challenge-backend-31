package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.detail;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record QueryProductOptionGroup(
    Long id,
    String name,
    Integer display_order,
    List<Options> options
) {

    @Builder
    public record Options(
        Long id,
        String name,
        BigDecimal additional_price,
        String sku,
        Integer stock,
        Integer display_order
    ) {
    }
}
