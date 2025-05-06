package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.detail;

import lombok.Builder;

@Builder
public record QueryProductImageDTO(
    Long id,
    String url,
    String alt_text,
    Boolean is_primary,
    Integer display_order,
    Long option_id
) {
}
