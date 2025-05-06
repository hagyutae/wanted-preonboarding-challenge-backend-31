package lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionGroupId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionId;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionDisplayOrderList;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionGroupDisplayOrderList;

public interface OptionValidator {
    void validOption(ProductOptionGroupDisplayOrderList list);

    void validOption(ProductOptionDisplayOrderList list);

    void validOption(ProductId productId, ProductOptionGroupId groupId);

    void validOption(ProductId productId, ProductOptionId optionId);
}
