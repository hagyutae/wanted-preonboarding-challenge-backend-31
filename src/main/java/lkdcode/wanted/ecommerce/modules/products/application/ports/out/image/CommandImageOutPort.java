package lkdcode.wanted.ecommerce.modules.products.application.ports.out.image;

import lkdcode.wanted.ecommerce.modules.products.application.usecase.image.AddImageResult;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.image.AddImageModel;
import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageDisplayOrder;

public interface CommandImageOutPort {
    AddImageResult add(AddImageModel model);

    void unsetPrimary(ProductId productId);

    void shiftDisplayOrder(ProductId productId, ProductImageDisplayOrder productImageDisplayOrder);
}
