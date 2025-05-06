package lkdcode.wanted.ecommerce.modules.products.domain.value.tag;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public record ProductTagList(
    List<ProductTag> list
) {

    public ProductTagList(List<ProductTag> list) {
        this.list = Objects.requireNonNull(list);
    }

    public Stream<ProductTag> stream() {
        return list.stream();
    }

    public void forEach(final Consumer<ProductTag> action) {
        list.forEach(action);
    }
}