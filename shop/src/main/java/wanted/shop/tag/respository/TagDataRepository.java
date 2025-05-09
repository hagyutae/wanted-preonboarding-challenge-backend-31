package wanted.shop.tag.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.shop.seller.domain.entity.Seller;
import wanted.shop.tag.domain.entity.Tag;

public interface TagDataRepository extends JpaRepository<Tag, Long> {
}
