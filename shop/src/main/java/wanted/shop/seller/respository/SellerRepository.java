package wanted.shop.seller.respository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.shop.seller.domain.entity.Seller;
import wanted.shop.seller.domain.entity.SellerId;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class SellerRepository {

    private SellerDataRepository dataRepository;

    public Optional<Seller> findById(SellerId sellerId) {
        return dataRepository.findById(sellerId.getValue());

    }

    public Seller save(Seller seller) {
        return dataRepository.save(seller);
    }
}
