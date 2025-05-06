package lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductSellerId;

public interface SellerValidator {
    void validId(final ProductSellerId target);

    void validAuthoritySeller(final ProductId productId, final ProductSellerId sellerId);
}
