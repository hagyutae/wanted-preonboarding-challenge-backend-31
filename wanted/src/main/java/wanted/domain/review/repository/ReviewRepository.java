package wanted.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wanted.domain.review.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);

    @Query("SELECT r FROM Review r " +
            "WHERE r.product.id = :productId " +
            "AND (:rating IS NULL OR r.rating = :rating) " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'created_at' AND :isDesc = true THEN r.createdAt END DESC, " +
            "CASE WHEN :sortBy = 'created_at' AND :isDesc = false THEN r.createdAt END ASC, " +
            "CASE WHEN :sortBy = 'rating' AND :isDesc = true THEN r.rating END DESC, " +
            "CASE WHEN :sortBy = 'rating' AND :isDesc = false THEN r.rating END ASC")
    List<Review> findPagedByProductId(Long productId, Integer rating, String sortBy, boolean isDesc, int offset, int limit);

    int countByProductIdAndRating(Long productId, Integer rating);
}
