package lkdcode.wanted.ecommerce.modules.products.domain.model.create;

import lkdcode.wanted.ecommerce.modules.products.domain.value.option.*;

public record ProductOptionModel(
    ProductOptionName optionName,
    ProductOptionAdditionalPrice additionalPrice,
    ProductOptionSku sku,
    ProductOptionStock stock,
    ProductOptionDisplayOrder displayOrder
) {
}