package lkdcode.wanted.ecommerce.modules.products.application.usecase.image;

import lombok.Builder;

@Builder
public record AddImageResult(
    Long id,
    String url,
    String alt_text,
    Boolean is_primary,
    Integer display_order,
    Integer option_id
) {
}