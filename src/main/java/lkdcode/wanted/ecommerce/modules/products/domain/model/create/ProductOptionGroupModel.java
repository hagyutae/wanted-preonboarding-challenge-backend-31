package lkdcode.wanted.ecommerce.modules.products.domain.model.create;

import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionGroupDisplayOrder;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionGroupName;

import java.util.function.Consumer;

public record ProductOptionGroupModel(
    ProductOptionGroupName groupName,
    ProductOptionGroupDisplayOrder groupDisplayOrder,

    ProductOptionList optionList
) {

    public void optionListForEach(Consumer<ProductOptionModel> action) {
        optionList.forEach(action);
    }
}
