package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query;

import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationException;
import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationResponseCode;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductOptionJpaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QueryProductOptionJpaRepository extends Repository<ProductOptionJpaEntity, Long> {
    Optional<ProductOptionJpaEntity> findById(Long id);

    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END FROM ProductOptionGroupJpaEntity g WHERE g.id IN :groupIdList")
    boolean existsInGroupIdList(@Param("groupIdList") List<Long> groupIdList);

    default ProductOptionJpaEntity loadById(Long id) {
        return findById(id)
            .orElseThrow(() -> new ApplicationException(ApplicationResponseCode.NOT_FOUND_OPTION));
    }
}
