package lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.update;

import lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.CommandOptionResult;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.option.UpdateOptionModel;

import java.util.function.BiConsumer;
import java.util.function.Function;

class UpdateOptionUsecase {
    private final ProductId productId;
    private final UpdateOptionModel model;
    private final ProductOptionId optionId;

    private CommandOptionResult result;

    private UpdateOptionUsecase(ProductId productId, UpdateOptionModel model) {
        this.productId = productId;
        this.model = model;
        this.optionId = model.optionId();
    }

    public static UpdateOptionUsecase execute(ProductId productId, UpdateOptionModel model) {
        return new UpdateOptionUsecase(productId, model);
    }

    public UpdateOptionUsecase validAuthority(final BiConsumer<ProductId, ProductOptionId> valid) {
        valid.accept(productId, optionId);
        return this;
    }

    public UpdateOptionUsecase update(final Function<UpdateOptionModel, CommandOptionResult> update) {
        this.result = update.apply(model);
        return this;
    }

    public CommandOptionResult done() {
        return result;
    }
}