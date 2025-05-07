package wanted.domain.product.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import wanted.domain.product.entity.ProductOption;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductOptionResponse(
        Long id,
        Long optionGroupId,
        String name,
        BigDecimal additionalPrice,
        String sku,
        Integer stock,
        Integer displayOrder
) {
    public static ProductOptionResponse of(ProductOption option, boolean groupRequired) {
        return new ProductOptionResponse(
                option.getId(),
                groupRequired? option.getOptionGroup().getId() : null,
                option.getName(),
                option.getAdditionalPrice(),
                option.getSku(),
                option.getStock(),
                option.getDisplayOrder()
        );
    }
}