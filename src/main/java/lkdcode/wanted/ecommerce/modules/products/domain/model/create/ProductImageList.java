package lkdcode.wanted.ecommerce.modules.products.domain.model.create;

import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageDisplayOrderList;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public record ProductImageList(
    List<ProductImageModel> list
) {

    public ProductImageList(List<ProductImageModel> list) {
        this.list = Objects.requireNonNull(list);
    }

    public ProductImageDisplayOrderList getProductImageDisplayOrderList() {
        return new ProductImageDisplayOrderList(
            list.stream()
                .map(ProductImageModel::displayOrder)
                .toList()
        );
    }

    public Stream<ProductImageModel> stream() {
        return list.stream();
    }

    public void forEach(Consumer<ProductImageModel> action) {
        list.forEach(action);
    }
}