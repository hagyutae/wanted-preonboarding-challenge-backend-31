package minseok.cqrschallenge.review.repository;

import java.util.List;
import minseok.cqrschallenge.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.product.id = :productId")
    Page<Review> findByProductIdWithPage(@Param("productId") Long productId, Pageable pageable);

    List<Review> findByProductId(Long productId);

    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND r.rating = :rating")
    Page<Review> findByProductIdAndRating(@Param("productId") Long productId,
        @Param("rating") Integer rating, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId")
    Double calculateAverageRating(@Param("productId") Long productId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :productId")
    Integer countByProductId(@Param("productId") Long productId);

    @Query("SELECT r.rating AS rating, COUNT(r) AS count FROM Review r WHERE r.product.id = :productId GROUP BY r.rating ORDER BY r.rating DESC")
    List<Object[]> countByRatingForProduct(@Param("productId") Long productId);

    @Query("SELECT r FROM Review r WHERE r.id = :id AND r.user.id = :userId")
    Review findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}