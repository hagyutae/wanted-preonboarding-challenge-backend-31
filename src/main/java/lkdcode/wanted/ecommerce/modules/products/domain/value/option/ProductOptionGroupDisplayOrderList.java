package lkdcode.wanted.ecommerce.modules.products.domain.value.option;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public record ProductOptionGroupDisplayOrderList(
    List<ProductOptionGroupDisplayOrder> list
) {

    public ProductOptionGroupDisplayOrderList(List<ProductOptionGroupDisplayOrder> list) {
        this.list = Objects.requireNonNull(list);
    }

    public Stream<ProductOptionGroupDisplayOrder> stream() {
        return list.stream();
    }

    public void forEach(final Consumer<ProductOptionGroupDisplayOrder> action) {
        list.forEach(action);
    }

    public int size() {
        return list.size();
    }
}
