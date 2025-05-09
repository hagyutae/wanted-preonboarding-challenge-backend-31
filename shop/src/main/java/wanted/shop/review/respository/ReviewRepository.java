package wanted.shop.review.respository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import wanted.shop.review.domain.entity.Review;

@Repository
@AllArgsConstructor
public class ReviewRepository {

    private ReviewDataRepository dataRepository;

    public Page<Review> findAll(Specification<Review> spec, Pageable pageable) {
        return dataRepository.findAll(spec, pageable);
    }
}
