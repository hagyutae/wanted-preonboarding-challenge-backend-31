package wanted.shop.brand.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.shop.brand.domain.entity.Brand;
import wanted.shop.seller.domain.entity.Seller;

public interface BrandDataRepository extends JpaRepository<Brand, Long> {
}
