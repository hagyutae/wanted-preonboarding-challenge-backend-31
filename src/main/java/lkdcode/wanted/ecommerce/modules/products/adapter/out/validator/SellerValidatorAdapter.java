package lkdcode.wanted.ecommerce.modules.products.adapter.out.validator;

import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationException;
import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationResponseCode;
import lkdcode.wanted.ecommerce.modules.products.adapter.external.seller.QuerySellerAdapter;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query.QueryProductJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.SellerValidator;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductSellerId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerValidatorAdapter implements SellerValidator {
    private final QuerySellerAdapter sellerAdapter;
    private final QueryProductJpaRepository productJpaRepository;

    @Override
    public void validId(ProductSellerId target) {
        if (sellerAdapter.load(target.value()) == null) {
            throw new ApplicationException(ApplicationResponseCode.NOT_FOUND_SELLER);
        }
    }

    @Override
    public void validAuthoritySeller(ProductId productId, ProductSellerId sellerId) {
        final var product = productJpaRepository.loadById(productId.value());

        if (!product.getSeller().getId().equals(sellerId.value())) {
            throw new ApplicationException(ApplicationResponseCode.INVALID_SELLER);
        }
    }
}
