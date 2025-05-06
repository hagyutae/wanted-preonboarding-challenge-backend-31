package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.detail;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder
public record QueryProductDetailDTO(
    BigDecimal weight,
    Map<String, Object> dimensions,
    String materials,
    String country_of_origin,
    String warranty_info,
    String care_instructions,
    Map<String, Object> additional_info
) {
}
