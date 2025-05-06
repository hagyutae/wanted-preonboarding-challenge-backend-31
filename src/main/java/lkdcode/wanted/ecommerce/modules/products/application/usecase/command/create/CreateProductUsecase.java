package lkdcode.wanted.ecommerce.modules.products.application.usecase.command.create;

import lkdcode.wanted.ecommerce.modules.products.application.usecase.command.UpsertResult;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductBrandId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductSellerId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.*;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductSlug;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageDisplayOrderList;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionDisplayOrderList;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionGroupDisplayOrderList;
import lkdcode.wanted.ecommerce.modules.products.domain.value.tag.ProductTagList;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

class CreateProductUsecase {
    private final ProductValues productValue;

    private final ProductCategoryList categoryList;
    private final ProductDetailModel detailModel;
    private final ProductOptionGroupList optionGroupList;

    private final ProductImageList imageList;
    private final ProductPriceModel priceModel;

    private final ProductTagList tagList;

    private UpsertResult result;
    private ProductId productId;

    private CreateProductUsecase(CreateProductModel model) {
        this.productValue = model.productValues();

        this.categoryList = model.categoryList();
        this.detailModel = model.detailModel();
        this.optionGroupList = model.optionGroupList();

        this.imageList = model.imageModel();
        this.priceModel = model.priceModel();

        this.tagList = model.tagList();
    }

    public static CreateProductUsecase execute(CreateProductModel model) {
        return new CreateProductUsecase(model);
    }

    /* Product */
    public CreateProductUsecase validBrandId(final Consumer<ProductBrandId> valid) {
        valid.accept(productValue.brandId());
        return this;
    }

    public CreateProductUsecase validSellerId(final Consumer<ProductSellerId> valid) {
        valid.accept(productValue.sellerId());
        return this;
    }

    public CreateProductUsecase validUniqueSlug(final Consumer<ProductSlug> valid) {
        valid.accept(productValue.slug());
        return this;
    }

    public CreateProductUsecase saveProduct(final Function<ProductValues, UpsertResult> save) {
        this.result = save.apply(productValue);
        this.productId = this.result.id();
        return this;
    }

    /* Category */
    public CreateProductUsecase validProductCategoryList(final Consumer<ProductCategoryList> valid) {
        valid.accept(categoryList);
        return this;
    }

    public CreateProductUsecase saveProductCategory(final BiConsumer<ProductId, ProductCategoryList> save) {
        save.accept(productId, categoryList);
        return this;
    }

    /* Detail */
    public CreateProductUsecase saveProductDetail(final BiConsumer<ProductId, ProductDetailModel> save) {
        save.accept(productId, detailModel);
        return this;
    }

    /* Option */
    public CreateProductUsecase validProductOptionGroup(final Consumer<ProductOptionGroupDisplayOrderList> valid) {
        valid.accept(optionGroupList.getProductOptionGroupDisplayOrderList());
        return this;
    }

    public CreateProductUsecase validProductOption(final Consumer<ProductOptionDisplayOrderList> valid) {
        optionGroupList.list()
            .forEach(e ->
                valid.accept(e.optionList().getProductOptionDisplayOrderList())
            );

        return this;
    }

    public CreateProductUsecase saveProductOption(final BiConsumer<ProductId, ProductOptionGroupList> save) {
        save.accept(productId, optionGroupList);
        return this;
    }

    /* Image */
    public CreateProductUsecase validImageDisplayOrder(final Consumer<ProductImageDisplayOrderList> valid) {
        valid.accept(imageList.getProductImageDisplayOrderList());
        return this;
    }

    public CreateProductUsecase saveImage(final BiConsumer<ProductId, ProductImageList> save) {
        save.accept(productId, imageList);
        return this;
    }

    /* Price */
    public CreateProductUsecase saveProductPrice(final BiConsumer<ProductId, ProductPriceModel> save) {
        save.accept(productId, priceModel);
        return this;
    }

    /* Tag */
    public CreateProductUsecase validProductTag(final Consumer<ProductTagList> valid) {
        valid.accept(tagList);
        return this;
    }

    public CreateProductUsecase saveProductTag(final BiConsumer<ProductId, ProductTagList> save) {
        save.accept(productId, tagList);
        return this;
    }

    public UpsertResult done() {
        return result;
    }
}