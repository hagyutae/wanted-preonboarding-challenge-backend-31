package wanted.shop.seller.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.shop.seller.domain.entity.Seller;

public interface SellerDataRepository extends JpaRepository<Seller, Long> {
}
