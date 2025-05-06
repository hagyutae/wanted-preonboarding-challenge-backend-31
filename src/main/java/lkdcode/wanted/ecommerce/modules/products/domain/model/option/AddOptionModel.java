package lkdcode.wanted.ecommerce.modules.products.domain.model.option;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionGroupId;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.*;

public record AddOptionModel(
    ProductOptionGroupId optionGroupId,
    ProductOptionName name,
    ProductOptionAdditionalPrice additionalPrice,
    ProductOptionSku sku,
    ProductOptionStock stock,
    ProductOptionDisplayOrder displayOrder
) {
}