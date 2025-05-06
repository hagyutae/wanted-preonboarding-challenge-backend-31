package lkdcode.wanted.ecommerce.modules.products.domain.model.create;

import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionGroupDisplayOrderList;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public record ProductOptionGroupList(
    List<ProductOptionGroupModel> list
) {

    public ProductOptionGroupList(List<ProductOptionGroupModel> list) {
        this.list = Objects.requireNonNull(list);
    }

    public ProductOptionGroupDisplayOrderList getProductOptionGroupDisplayOrderList() {
        return new ProductOptionGroupDisplayOrderList(
            list.stream()
                .map(ProductOptionGroupModel::groupDisplayOrder)
                .toList()
        );
    }

    public Stream<ProductOptionGroupModel> stream() {
        return list.stream();
    }

    public void forEach(final Consumer<ProductOptionGroupModel> action) {
        list.forEach(action);
    }
}