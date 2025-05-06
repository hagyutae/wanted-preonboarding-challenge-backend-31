package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.detail;

import lombok.Builder;

@Builder
public record QueryProductTagDTO(
    Long id,
    String name,
    String slug
) {
}
