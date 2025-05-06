package lkdcode.wanted.ecommerce.modules.products.domain.model.create;

import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionDisplayOrderList;

import java.util.List;
import java.util.function.Consumer;

public record ProductOptionList(
    List<ProductOptionModel> list
) {

    public ProductOptionDisplayOrderList getProductOptionDisplayOrderList() {
        return new ProductOptionDisplayOrderList(list
            .stream()
            .map(ProductOptionModel::displayOrder)
            .toList()
        );
    }

    public void forEach(final Consumer<ProductOptionModel> action) {
        list.forEach(action);
    }
}
