package lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductSlug;

public interface ProductValidator {
    void validUniqueSlug(ProductSlug target);
    void validUniqueSlugForUpdate(ProductId id, ProductSlug target);

    void existsProduct(ProductId id);
}
