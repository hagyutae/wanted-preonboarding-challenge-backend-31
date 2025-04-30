package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.query;

import static com.example.wanted_preonboarding_challenge_backend_31.domain.model.review.QReview.review;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.review.Review;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.QuerydslRepositorySupport;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.review.query.dto.ProductReviewSummaryDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationReq;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationRes;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductRatingDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductRatingDistributionDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.review.ProductReviewSearchDataDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.user.UserInfoDto;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductReviewSearchReq;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @Override
    public ProductRatingDetailDto getProductRatingDetail(Long productId) {
        ProductRatingDetailDto productRatingDetailDto = select(Projections.constructor(
                ProductRatingDetailDto.class,
                review.rating.avg(),
                review.count(),
                Expressions.nullExpression(ProductRatingDistributionDto.class)
        ))
                .from(review)
                .where(review.product.id.eq(productId))
                .fetchOne();

        return ProductRatingDetailDto.from(Objects.requireNonNull(productRatingDetailDto),
                getRatingDistribution(productId));
    }

    private ProductRatingDistributionDto getRatingDistribution(Long productId) {
        List<Tuple> ratingCounts = queryFactory()
                .select(review.rating, review.count())
                .from(review)
                .where(review.product.id.eq(productId))
                .groupBy(review.rating)
                .fetch();

        Map<Integer, Integer> distributionMap = new HashMap<>();
        for (Tuple tuple : ratingCounts) {
            Integer rating = tuple.get(review.rating);
            Integer ratingCount = tuple.get(review.count()).intValue();
            distributionMap.put(rating, ratingCount);
        }

        return new ProductRatingDistributionDto(
                distributionMap.getOrDefault(5, 0),
                distributionMap.getOrDefault(4, 0),
                distributionMap.getOrDefault(3, 0),
                distributionMap.getOrDefault(2, 0),
                distributionMap.getOrDefault(1, 0)
        );
    }

    @Override
    public List<ProductReviewSearchDataDto> getSearchProductReviews(Long productId, PaginationReq paginationReq,
                                                                    ProductReviewSearchReq req) {
        return select(Projections.constructor(
                ProductReviewSearchDataDto.class,
                review.id,
                Projections.constructor(
                        UserInfoDto.class,
                        review.user.id,
                        review.user.name,
                        review.user.avatarUrl
                ),
                review.rating,
                review.title,
                review.content,
                review.createdAt,
                review.updatedAt,
                review.verifiedPurchase,
                review.helpfulVotes
        ))
                .from(review)
                .join(review.user)
                .where(ratingFilter(req.rating()))
                .orderBy(createOrderSpecifier(req.sort()))
                .offset(paginationReq.getOffset())
                .limit(paginationReq.perPage())
                .fetch();
    }

    @Override
    public PaginationRes countSearchProductReviews(Long productId, PaginationReq paginationReq,
                                                   ProductReviewSearchReq req) {
        Long totalItems = select(review.count())
                .from(review)
                .join(review.user)
                .where(ratingFilter(req.rating()))
                .fetchOne();

        assert totalItems != null;
        return PaginationRes.of(totalItems.intValue(), paginationReq.page(), paginationReq.perPage());
    }

    private BooleanExpression ratingFilter(Integer rating) {
        return rating == null ? null : review.rating.eq(rating);
    }
}
