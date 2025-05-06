package lkdcode.wanted.ecommerce.modules.products.application.ports.out.command;

import lkdcode.wanted.ecommerce.modules.products.application.usecase.command.UpsertResult;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.*;
import lkdcode.wanted.ecommerce.modules.products.domain.value.tag.ProductTagList;

public interface CreateProductOutPort {
    UpsertResult save(ProductValues values);

    void saveCategory(ProductId id, ProductCategoryList list);

    void saveDetail(ProductId id, ProductDetailModel model);

    void saveOption(ProductId id, ProductOptionGroupList list);

    void saveImage(ProductId id, ProductImageList list);

    void savePrice(ProductId id, ProductPriceModel model);

    void saveTag(ProductId id, ProductTagList list);
}