package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query;

import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationException;
import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationResponseCode;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductDetailJpaEntity;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductJpaEntity;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.detail.QueryProductOptionGroup;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface QueryProductDetailJpaRepository extends Repository<ProductDetailJpaEntity, Long> {
    void save(ProductDetailJpaEntity entity);

    Optional<ProductDetailJpaEntity> findByProduct_Id(Long productId);

    default ProductDetailJpaEntity loadByProductId(final Long productId) {
        return findByProduct_Id(productId)
            .orElseThrow(() -> new ApplicationException(ApplicationResponseCode.NOT_FOUND_DETAIL));
    }
}
