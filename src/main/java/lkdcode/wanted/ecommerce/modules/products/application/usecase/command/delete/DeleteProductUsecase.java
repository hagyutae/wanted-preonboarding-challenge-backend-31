package lkdcode.wanted.ecommerce.modules.products.application.usecase.command.delete;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;

import java.util.function.Consumer;

class DeleteProductUsecase {
    private final ProductId productId;

    private DeleteProductUsecase(ProductId productId) {
        this.productId = productId;
    }

    public static DeleteProductUsecase execute(ProductId productId) {
        return new DeleteProductUsecase(productId);
    }

    public DeleteProductUsecase delete(final Consumer<ProductId> delete) {
        delete.accept(productId);
        return this;
    }

    public void done() {

    }
}
