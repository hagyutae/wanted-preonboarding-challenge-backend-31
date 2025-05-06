package lkdcode.wanted.ecommerce.modules.products.adapter.out.validator;

import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationException;
import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationResponseCode;
import lkdcode.wanted.ecommerce.modules.products.adapter.external.brand.QueryBrandAdapter;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query.QueryProductJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.BrandValidator;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductBrandId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandValidatorAdapter implements BrandValidator {
    private final QueryBrandAdapter adapter;
    private final QueryProductJpaRepository productJpaRepository;

    @Override
    public void validId(ProductBrandId target) {
        final var entity = adapter.load(target.value());

        if (entity == null) {
            throw new ApplicationException(ApplicationResponseCode.NOT_FOUND_BRAND);
        }
    }

    @Override
    public void validId(ProductId productId, ProductBrandId brandId) {
        final var product = productJpaRepository.loadById(productId.value());
        if (!product.getBrand().getId().equals(brandId.value())) {
            throw new ApplicationException(ApplicationResponseCode.NOT_FOUND_BRAND);
        }
    }
}