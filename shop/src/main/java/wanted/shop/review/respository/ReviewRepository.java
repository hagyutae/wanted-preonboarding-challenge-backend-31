package wanted.shop.review.respository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.domain.entity.ReviewId;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ReviewRepository {

    private ReviewDataRepository dataRepository;

    public Page<Review> findAll(Specification<Review> spec, Pageable pageable) {
        return dataRepository.findAll(spec, pageable);
    }

    public Optional<Review> findById(ReviewId reviewId) {
        return dataRepository.findById(reviewId.getValue());
    }

    public Review save(Review review) {
        return dataRepository.save(review);
    }
}
