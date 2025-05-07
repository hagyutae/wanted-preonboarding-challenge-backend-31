package wanted.domain.product.dto.response;

import wanted.domain.product.entity.ProductPrice;

import java.math.BigDecimal;

public record ProductPriceResponse(
        BigDecimal basePrice,
        BigDecimal salePrice,
        String currency,
        BigDecimal taxRate,
        Integer discountPercentage
) {
    public static ProductPriceResponse of(ProductPrice price) {
        return new ProductPriceResponse(
                price.getBasePrice(),
                price.getSalePrice(),
                price.getCurrency(),
                price.getTaxRate(),
                price.calculateDiscountPercentage()
        );
    }
}
