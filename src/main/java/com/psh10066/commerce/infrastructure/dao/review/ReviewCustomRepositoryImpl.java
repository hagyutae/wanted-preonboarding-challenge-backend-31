package com.psh10066.commerce.infrastructure.dao.review;

import com.psh10066.commerce.api.dto.request.GetProductReviewsRequest;
import com.psh10066.commerce.api.dto.response.GetAllReviewsResponse;
import com.psh10066.commerce.domain.model.product.ProductStatus;
import com.psh10066.commerce.domain.model.review.Review;
import com.psh10066.commerce.infrastructure.dao.common.OrderGenerator;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.psh10066.commerce.domain.model.product.QProduct.product;
import static com.psh10066.commerce.domain.model.review.QReview.review;
import static com.psh10066.commerce.domain.model.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GetAllReviewsResponse> getProductReviews(Long productId, GetProductReviewsRequest request) {
        Pageable pageable = request.toPageable();

        List<GetAllReviewsResponse> result = queryFactory.select(Projections.constructor(GetAllReviewsResponse.class,
                review.id,
                Projections.constructor(GetAllReviewsResponse.UserDto.class, user.id, user.name, user.avatarUrl),
                review.rating,
                review.title,
                review.content,
                review.createdAt,
                review.updatedAt,
                review.verifiedPurchase,
                review.helpfulVotes
            ))
            .from(review)
            .join(review.user, user)
            .join(review.product, product)
            .where(
                product.id.eq(productId),
                product.status.ne(ProductStatus.DELETED),
                request.getRating() != null ? review.rating.eq(request.getRating()) : null
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(OrderGenerator.generateOrders(pageable.getSort(), Review.class))
            .fetch();

        return PageableExecutionUtils.getPage(result, pageable, () ->
            queryFactory.select(review.count())
                .from(review)
                .join(review.product, product)
                .where(
                    product.id.eq(productId),
                    product.status.ne(ProductStatus.DELETED),
                    request.getRating() != null ? review.rating.eq(request.getRating()) : null
                )
                .fetchOne()
        );
    }
}
