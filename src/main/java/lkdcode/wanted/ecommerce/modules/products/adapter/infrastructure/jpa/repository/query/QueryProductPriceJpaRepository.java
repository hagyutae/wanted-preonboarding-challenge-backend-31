package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query;

import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationException;
import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationResponseCode;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductPriceJpaEntity;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface QueryProductPriceJpaRepository extends Repository<ProductPriceJpaEntity, Long> {
    Optional<ProductPriceJpaEntity> findByProduct_id(Long productId);

    default ProductPriceJpaEntity loadByProductId(Long productId) {
        return findByProduct_id(productId)
            .orElseThrow(() -> new ApplicationException(ApplicationResponseCode.NOT_FOUND_PRICE));
    }
}
