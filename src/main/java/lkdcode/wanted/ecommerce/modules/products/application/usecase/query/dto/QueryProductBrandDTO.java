package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record QueryProductBrandDTO(
    Long id,
    String name,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String description,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String logo_url,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String website
) {
}
