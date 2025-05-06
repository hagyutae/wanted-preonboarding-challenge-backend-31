package lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query;

import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationException;
import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationResponseCode;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QueryProductJpaRepository extends Repository<ProductJpaEntity, Long> {
    Optional<ProductJpaEntity> findById(Long id);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM products WHERE slug = :slug LIMIT 1)", nativeQuery = true)
    boolean existsSlug(@Param("slug") String slug);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM products WHERE id != :id AND slug = :slug LIMIT 1)", nativeQuery = true)
    boolean existsSlugForUpdate(@Param("id") Long id, @Param("slug") String slug);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM products WHERE id = :id AND status != 'DELETED' LIMIT 1)", nativeQuery = true)
    boolean existsId(@Param("id") Long id);

    default ProductJpaEntity loadById(final Long id) {
        return findById(id)
            .orElseThrow(() -> new ApplicationException(ApplicationResponseCode.NOT_FOUND_PRODUCT));
    }
}