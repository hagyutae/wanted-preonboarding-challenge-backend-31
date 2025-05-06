package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.detail;

import lombok.Builder;

import java.util.Map;

@Builder
public record QueryProductRatingDTO(
    Double average,
    Integer count,
    Map<String, Long> distribution
) {
}
