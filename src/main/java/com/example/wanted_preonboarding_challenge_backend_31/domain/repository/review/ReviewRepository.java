package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.review.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r.product.id "
            + "from Review r "
            + "group by r.product.id "
//            + "having count(r) >= 10 "
            + "order by avg(r.rating) desc "
            + "limit :limit")
    List<Long> findPopularProductIds(@Param("limit") int limit);
}
