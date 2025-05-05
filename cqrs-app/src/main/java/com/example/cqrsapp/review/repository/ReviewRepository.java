package com.example.cqrsapp.review.repository;

import com.example.cqrsapp.review.domain.Review;
import com.example.cqrsapp.common.dto.ReviewRatingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = """
            SELECT new com.example.cqrsapp.common.dto.ReviewRatingDto(avg(r.rating),count(r))
             FROM Review r
            WHERE r.product.id = :id
            """)
    ReviewRatingDto findReviewRatingByProductId(@Param("id") Long id);

    @Query(value = """
            SELECT new com.example.cqrsapp.common.dto.ReviewRatingDto$DistributionDto(r.rating, count(r))
             FROM Review r
            WHERE r.product.id = :id
            group by r.rating
            """)
    List<ReviewRatingDto.DistributionDto> findReviewDistributionByProductId(@Param("id") Long id);


    @Query(value = """
            SELECT new com.example.cqrsapp.common.dto.ReviewRatingDto(avg(r.rating),count(r))
             FROM Review r
            WHERE r.product.id = :id and r.rating >= :rating
            """)
    ReviewRatingDto findReviewRatingByProductIdAndRating(@Param("id") Long id,  @Param("rating") Integer rating);

    @Query(value = """
            SELECT new com.example.cqrsapp.common.dto.ReviewRatingDto$DistributionDto(r.rating, count(r))
             FROM Review r
            WHERE r.product.id = :id and r.rating >= :rating
            group by r.rating
            """)
    List<ReviewRatingDto.DistributionDto> findReviewDistributionByProductIdAndRating(@Param("id") Long id,  @Param("rating") Integer rating);

    @Query(value = """
        SELECT r FROM Review r
        JOIN FETCH r.user u
        WHERE r.product.id = :productId AND r.rating >= :rating
    """, countQuery = """
        SELECT COUNT(r) FROM Review r
        WHERE r.product.id = :productId AND r.rating >= :rating
    """)
    Page<Review> findByProductIdAndRating(@Param("productId") Long productId,
                                          @Param("rating") int rating,
                                          Pageable pageable);
}


