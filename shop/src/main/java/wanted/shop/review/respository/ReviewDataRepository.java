package wanted.shop.review.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.domain.entity.ReviewId;

public interface ReviewDataRepository extends JpaRepository<Review, ReviewId> {
    Page<Review> findAll(Specification spec, Pageable pageable);
}
