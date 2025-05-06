package lkdcode.wanted.ecommerce.modules.products.application.usecase.command.update;

import lkdcode.wanted.ecommerce.modules.products.application.usecase.command.UpsertResult;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductBrandId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductSellerId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.*;
import lkdcode.wanted.ecommerce.modules.products.domain.model.update.UpdateProductModel;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductSlug;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageDisplayOrderList;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionDisplayOrderList;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionGroupDisplayOrderList;
import lkdcode.wanted.ecommerce.modules.products.domain.value.tag.ProductTagList;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

class UpdateProductUsecase {
    private final ProductId productId;
    private final ProductValues productValue;

    private final ProductCategoryList categoryList;
    private final ProductDetailModel detailModel;
    private final ProductOptionGroupList optionGroupList;

    private final ProductImageList imageList;
    private final ProductPriceModel priceModel;

    private final ProductTagList tagList;


    private UpsertResult result;

    private UpdateProductUsecase(ProductId productId, UpdateProductModel model) {
        this.productId = productId;
        this.productValue = model.productValues();

        this.categoryList = model.categoryList();
        this.detailModel = model.detailModel();
        this.optionGroupList = model.optionGroupList();

        this.imageList = model.imageModel();
        this.priceModel = model.priceModel();

        this.tagList = model.tagList();
    }

    public static UpdateProductUsecase execute(ProductId productId, UpdateProductModel model) {
        return new UpdateProductUsecase(productId, model);
    }

    public UpdateProductUsecase validBrandId(final BiConsumer<ProductId, ProductBrandId> valid) {
        valid.accept(productId, productValue.brandId());
        return this;
    }

    public UpdateProductUsecase validAuthoritySeller(final BiConsumer<ProductId, ProductSellerId> valid) {
        valid.accept(productId, productValue.sellerId());
        return this;
    }

    public UpdateProductUsecase validUniqueSlug(final BiConsumer<ProductId, ProductSlug> valid) {
        valid.accept(productId, productValue.slug());
        return this;
    }

    public UpdateProductUsecase updateProduct(final BiFunction<ProductId, ProductValues, UpsertResult> save) {
        this.result = save.apply(productId, productValue);
        return this;
    }

    public UpdateProductUsecase validProductCategoryList(final Consumer<ProductCategoryList> valid) {
        valid.accept(categoryList);
        return this;
    }

    public UpdateProductUsecase updateProductCategory(final BiConsumer<ProductId, ProductCategoryList> save) {
        save.accept(productId, categoryList);
        return this;
    }

    public UpdateProductUsecase updateProductDetail(final BiConsumer<ProductId, ProductDetailModel> save) {
        save.accept(productId, detailModel);
        return this;
    }

    public UpdateProductUsecase validProductOptionGroup(final Consumer<ProductOptionGroupDisplayOrderList> valid) {
        valid.accept(optionGroupList.getProductOptionGroupDisplayOrderList());
        return this;
    }

    public UpdateProductUsecase validProductOption(final Consumer<ProductOptionDisplayOrderList> valid) {
        optionGroupList.list()
            .forEach(e ->
                valid.accept(e.optionList().getProductOptionDisplayOrderList())
            );

        return this;
    }

    public UpdateProductUsecase updateProductOption(final BiConsumer<ProductId, ProductOptionGroupList> save) {
        save.accept(productId, optionGroupList);
        return this;
    }

    public UpdateProductUsecase validImageDisplayOrder(final Consumer<ProductImageDisplayOrderList> valid) {
        valid.accept(imageList.getProductImageDisplayOrderList());
        return this;
    }

    public UpdateProductUsecase updateImage(final BiConsumer<ProductId, ProductImageList> save) {
        save.accept(productId, imageList);
        return this;
    }

    public UpdateProductUsecase updateProductPrice(final BiConsumer<ProductId, ProductPriceModel> save) {
        save.accept(productId, priceModel);
        return this;
    }

    public UpdateProductUsecase validProductTag(final Consumer<ProductTagList> valid) {
        valid.accept(tagList);
        return this;
    }

    public UpdateProductUsecase updateProductTag(final BiConsumer<ProductId, ProductTagList> save) {
        save.accept(productId, tagList);
        return this;
    }

    public UpsertResult done() {
        return result;
    }
}