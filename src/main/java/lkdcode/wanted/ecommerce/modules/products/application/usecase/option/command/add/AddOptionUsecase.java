package lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.add;

import lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command.CommandOptionResult;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionGroupId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.option.AddOptionModel;

import java.util.function.BiConsumer;
import java.util.function.Function;

class AddOptionUsecase {
    private final ProductId productId;
    private final AddOptionModel model;

    private CommandOptionResult result;

    private AddOptionUsecase(ProductId productId, AddOptionModel model) {
        this.productId = productId;
        this.model = model;
    }

    public static AddOptionUsecase execute(final ProductId productId, final AddOptionModel model) {
        return new AddOptionUsecase(productId, model);
    }

    public AddOptionUsecase validOptionGroupId(final BiConsumer<ProductId, ProductOptionGroupId> valid) {
        valid.accept(productId, model.optionGroupId());
        return this;
    }

    public AddOptionUsecase addOption(final Function<AddOptionModel, CommandOptionResult> add) {
        this.result = add.apply(model);
        return this;
    }

    public CommandOptionResult done() {
        return result;
    }
}