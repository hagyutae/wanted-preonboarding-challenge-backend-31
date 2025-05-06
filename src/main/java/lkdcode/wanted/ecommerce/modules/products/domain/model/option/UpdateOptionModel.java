package lkdcode.wanted.ecommerce.modules.products.domain.model.option;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionId;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.*;

public record UpdateOptionModel(
    ProductOptionId optionId,
    ProductOptionName name,
    ProductOptionAdditionalPrice additionalPrice,
    ProductOptionSku sku,
    ProductOptionStock stock,
    ProductOptionDisplayOrder displayOrder
) {
}
