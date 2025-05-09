package wanted.shop.review.respository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.domain.entity.ReviewId;

@Repository
@AllArgsConstructor
public class ReviewRepository {

    private ReviewDataRepository dataRepository;

    public Page<Review> findAll(Specification<Review> spec, Pageable pageable) {
        return dataRepository.findAll(spec, pageable);
    }

    public Review findById(ReviewId reviewId) {
        return dataRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("없는 리뷰입니다"));
    }

    public void save(Review review) {
        dataRepository.save(review);
    }
}
