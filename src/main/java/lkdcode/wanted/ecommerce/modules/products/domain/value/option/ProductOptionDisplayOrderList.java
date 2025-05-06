package lkdcode.wanted.ecommerce.modules.products.domain.value.option;

import java.util.List;
import java.util.function.Consumer;

public record ProductOptionDisplayOrderList(
    List<ProductOptionDisplayOrder> list
) {

    public void forEach(final Consumer<ProductOptionDisplayOrder> action) {
        list.forEach(action);
    }

    public int size() {
        return list.size();
    }
}