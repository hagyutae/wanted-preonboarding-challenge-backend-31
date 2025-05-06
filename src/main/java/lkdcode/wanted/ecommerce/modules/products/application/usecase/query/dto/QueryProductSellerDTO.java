package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record QueryProductSellerDTO(
    Long id,
    String name,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String description,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String logo_url,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    BigDecimal rating,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String contact_email,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String contact_phone
) {
}
