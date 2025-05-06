package lkdcode.wanted.ecommerce.modules.products.application.ports.out.command;

import lkdcode.wanted.ecommerce.modules.products.application.usecase.command.UpsertResult;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.*;
import lkdcode.wanted.ecommerce.modules.products.domain.value.tag.ProductTagList;

public interface UpdateProductOutPort {
    UpsertResult update(ProductId id, ProductValues values);

    void updateCategory(ProductId id, ProductCategoryList list);

    void updateDetail(ProductId id, ProductDetailModel model);

    void updateOption(ProductId id, ProductOptionGroupList list);

    void updateImage(ProductId id, ProductImageList list);

    void updatePrice(ProductId id, ProductPriceModel model);

    void updateTag(ProductId id, ProductTagList list);
}
