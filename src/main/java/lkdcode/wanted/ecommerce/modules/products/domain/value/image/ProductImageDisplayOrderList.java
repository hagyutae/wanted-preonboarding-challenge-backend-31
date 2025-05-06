package lkdcode.wanted.ecommerce.modules.products.domain.value.image;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public record ProductImageDisplayOrderList(
    List<ProductImageDisplayOrder> list
) {

    public ProductImageDisplayOrderList(List<ProductImageDisplayOrder> list) {
        this.list = Objects.requireNonNull(list);
    }

    public Stream<ProductImageDisplayOrder> stream() {
        return list.stream();
    }

    public int size() {
        return list.size();
    }

    public void forEach(final Consumer<ProductImageDisplayOrder> action) {
        list.forEach(action);
    }
}