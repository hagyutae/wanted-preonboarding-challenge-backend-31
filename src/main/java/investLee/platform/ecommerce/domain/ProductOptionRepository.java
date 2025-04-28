package investLee.platform.ecommerce.domain;

import investLee.platform.ecommerce.domain.entity.ProductOptionEntity;
import investLee.platform.ecommerce.domain.entity.ProductOptionGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOptionEntity, Long> {
    void deleteAllByOptionGroup(ProductOptionGroupEntity optionGroup);
    // 특정 상품 ID에 속한 모든 옵션 조회
    List<ProductOptionEntity> findByOptionGroupProductId(Long productId);
    // 옵션 그룹 ID로 옵션들 조회
    List<ProductOptionEntity> findByOptionGroupId(Long optionGroupId);
}