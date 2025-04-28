package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.query;

import static com.example.wanted_preonboarding_challenge_backend_31.domain.model.review.QReview.review;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.review.Review;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.QuerydslRepositorySupport;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.query.dto.ProductReviewSummaryDto;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewQueryRepositoryImpl extends QuerydslRepositorySupport implements ReviewQueryRepository {

    protected ReviewQueryRepositoryImpl() {
        super(Review.class);
    }

    @Override
    public Map<Long, ProductReviewSummaryDto> getProductReviewSummaries(List<Long> productIds) {
        return queryFactory()
                .select(review.product.id, review.rating.avg(), review.count())
                .from(review)
                .where(review.product.id.in(productIds))
                .groupBy(review.product.id)
                .transform(GroupBy.groupBy(review.product.id).as(
                                Projections.constructor(
                                        ProductReviewSummaryDto.class,
                                        review.rating.avg(),
                                        review.count()
                                )
                        )
                );
    }
}
