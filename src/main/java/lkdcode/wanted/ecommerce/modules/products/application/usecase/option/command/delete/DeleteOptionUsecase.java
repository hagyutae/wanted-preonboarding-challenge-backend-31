package lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.delete;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionId;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

class DeleteOptionUsecase {
    private final ProductId productId;
    private final ProductOptionId optionId;

    private DeleteOptionUsecase(ProductId productId, ProductOptionId optionId) {
        this.productId = productId;
        this.optionId = optionId;
    }

    public static DeleteOptionUsecase execute(ProductId productId, ProductOptionId optionId) {
        return new DeleteOptionUsecase(productId, optionId);
    }

    public DeleteOptionUsecase validateOptionInProduct(final BiConsumer<ProductId, ProductOptionId> valid) {
        valid.accept(productId, optionId);
        return this;
    }

    public void delete(final Consumer<ProductOptionId> delete) {
        delete.accept(optionId);
    }
}