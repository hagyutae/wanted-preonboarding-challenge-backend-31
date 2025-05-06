package lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator;

import lkdcode.wanted.ecommerce.modules.products.domain.value.image.ProductImageDisplayOrderList;

public interface ImageValidator {
    void validImageDisplayOrder(ProductImageDisplayOrderList list);
}
