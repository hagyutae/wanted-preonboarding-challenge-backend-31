package investLee.platform.ecommerce.domain;

import investLee.platform.ecommerce.domain.entity.ProductEntity;
import investLee.platform.ecommerce.domain.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {
    void deleteAllByProduct(ProductEntity product);
    // 특정 상품 ID에 속한 모든 이미지 조회
    List<ProductImageEntity> findByProductId(Long productId);
    // 특정 옵션에 연결된 이미지 조회 (옵션 ID가 있는 경우)
    List<ProductImageEntity> findByOptionId(Long optionId);
}