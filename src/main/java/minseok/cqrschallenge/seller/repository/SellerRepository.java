package minseok.cqrschallenge.seller.repository;

import minseok.cqrschallenge.seller.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {

}
