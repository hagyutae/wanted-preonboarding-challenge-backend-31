package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query;

import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationException;
import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationResponseCode;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductOptionGroupJpaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QueryProductOptionGroupJpaRepository extends Repository<ProductOptionGroupJpaEntity, Long> {

    List<ProductOptionGroupJpaEntity> findAllByProduct_id(Long id);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM product_option_groups WHERE product_id = :productId AND id = :groupId LIMIT 1)", nativeQuery = true)
    boolean existsId(@Param("productId") Long productId, @Param("groupId") Long groupId);

    Optional<ProductOptionGroupJpaEntity> findById(Long id);

    default ProductOptionGroupJpaEntity loadById(Long id) {
        return findById(id)
            .orElseThrow(() -> new ApplicationException(ApplicationResponseCode.NOT_FOUND_OPTION_GROUP));
    }
}
