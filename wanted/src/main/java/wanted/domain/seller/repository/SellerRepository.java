package wanted.domain.seller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.domain.seller.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
