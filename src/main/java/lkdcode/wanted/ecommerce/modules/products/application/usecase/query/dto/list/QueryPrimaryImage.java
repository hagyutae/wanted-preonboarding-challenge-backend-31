package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.list;

import lombok.Builder;

@Builder
public record QueryPrimaryImage(
    String url,
    String alt_text
) {
}