package wanted.shop.tag.respository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import wanted.shop.seller.domain.entity.Seller;
import wanted.shop.seller.domain.entity.SellerId;
import wanted.shop.tag.domain.entity.Tag;
import wanted.shop.tag.domain.entity.TagId;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class TagRepository {

    private TagDataRepository dataRepository;

    public Optional<Tag> findById(TagId tagId) {
        return dataRepository.findById(tagId.getValue());

    }

    public Tag save(Tag tag) {
        return dataRepository.save(tag);
    }
}
