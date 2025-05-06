package lkdcode.wanted.ecommerce.modules.products.domain.model.create;

import lkdcode.wanted.ecommerce.modules.products.domain.value.price.*;

public record ProductPriceModel(
    ProductBasePrice basePrice,
    ProductSalePrice salePrice,
    ProductCostPrice costPrice,
    ProductCurrency currency,
    ProductTaxRate taxRate
) {
}
