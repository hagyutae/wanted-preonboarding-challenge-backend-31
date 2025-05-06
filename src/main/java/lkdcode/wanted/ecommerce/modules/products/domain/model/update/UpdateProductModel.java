package lkdcode.wanted.ecommerce.modules.products.domain.model.update;

import lkdcode.wanted.ecommerce.modules.products.domain.model.create.*;
import lkdcode.wanted.ecommerce.modules.products.domain.value.tag.ProductTagList;
import lombok.Builder;

import java.util.Objects;

public record UpdateProductModel(
    ProductValues productValues,

    ProductCategoryList categoryList,
    ProductDetailModel detailModel,
    ProductImageList imageModel,

    ProductPriceModel priceModel,
    ProductOptionGroupList optionGroupList,
    ProductTagList tagList
) {

    @Builder
    public UpdateProductModel(ProductValues productValues, ProductCategoryList categoryList, ProductDetailModel detailModel, ProductImageList imageModel, ProductPriceModel priceModel, ProductOptionGroupList optionGroupList, ProductTagList tagList) {
        this.productValues = Objects.requireNonNull(productValues);
        this.categoryList = Objects.requireNonNull(categoryList);
        this.detailModel = Objects.requireNonNull(detailModel);
        this.imageModel = Objects.requireNonNull(imageModel);
        this.priceModel = Objects.requireNonNull(priceModel);
        this.optionGroupList = Objects.requireNonNull(optionGroupList);
        this.tagList = Objects.requireNonNull(tagList);
    }
}