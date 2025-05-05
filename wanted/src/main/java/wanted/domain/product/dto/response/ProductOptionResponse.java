package wanted.domain.product.dto.response;

import wanted.domain.product.entity.ProductOption;

import java.math.BigDecimal;

public record ProductOptionResponse(
        Long id,
        String name,
        BigDecimal additionalPrice,
        String sku,
        Integer stock,
        Integer displayOrder
) {
    public static ProductOptionResponse of(ProductOption option) {
        return new ProductOptionResponse(
                option.getId(),
                option.getName(),
                option.getAdditionalPrice(),
                option.getSku(),
                option.getStock(),
                option.getDisplayOrder()
        );
    }
}