package lkdcode.wanted.ecommerce.modules.products.domain.model.create;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public record ProductCategoryList(
    List<ProductCategoryModel> list
) {
    public ProductCategoryList(List<ProductCategoryModel> list) {
        this.list = Objects.requireNonNull(list);
    }

    public Stream<ProductCategoryModel> stream() {
        return list.stream();
    }

    public void forEach(final Consumer<ProductCategoryModel> action) {
        list.forEach(action);
    }
}