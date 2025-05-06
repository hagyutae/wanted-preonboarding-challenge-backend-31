package lkdcode.wanted.ecommerce.modules.products.application.usecase.image;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.image.AddImageModel;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageDisplayOrder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

class AddImageUsecase {
    private final AddImageModel model;
    private AddImageResult result;

    public AddImageUsecase(AddImageModel model) {
        this.model = model;
    }

    public static AddImageUsecase execute(AddImageModel model) {
        return new AddImageUsecase(model);
    }

    public AddImageUsecase validProductId(final Consumer<ProductId> valid) {
        valid.accept(model.productId());
        return this;
    }

    public AddImageUsecase validateOptionInProduct(final BiConsumer<ProductId, ProductOptionId> valid) {
        if (model.optionId() != null) {
            valid.accept(model.productId(), model.optionId());
        }
        return this;
    }

    public AddImageUsecase unsetPrimary(final Consumer<ProductId> change) {
        if (model.isPrimary().value()) {
            change.accept(model.productId());
        }

        return this;
    }

    public AddImageUsecase shiftDisplayOrder(final BiConsumer<ProductId, ProductImageDisplayOrder> shift) {
        shift.accept(model.productId(), model.displayOrder());
        return this;
    }

    public AddImageUsecase add(final Function<AddImageModel, AddImageResult> add) {
        this.result = add.apply(model);
        return this;
    }

    public AddImageResult done() {
        return result;
    }
}