package lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductBrandId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;

public interface BrandValidator {
    void validId(final ProductBrandId target);

    void validId(final ProductId productId, final ProductBrandId brandId);
}
