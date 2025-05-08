package com.example.demo.review.repository;

import com.example.demo.review.dto.*;
import com.example.demo.review.entity.ReviewEntity;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.example.demo.review.entity.QReviewEntity.reviewEntity;
import static com.example.demo.user.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory qf;

    public List<ReviewStatistic> findAllByProductIds(Collection<Long> productIds) {
        return qf.select(new QReviewStatistic(reviewEntity.productEntity.id, reviewEntity.count(), reviewEntity.rating.avg()))
                .from(reviewEntity)
                .where(reviewEntity.productEntity.id.in(productIds))
                .groupBy(reviewEntity.productEntity)
                .fetch();
    }

    @Override
    public ReviewDistribution findDistributionByProductId(Long productId) {
        NumberExpression<Long> fiveStar = new CaseBuilder()
                .when(reviewEntity.rating.eq(5)).then(1L)
                .otherwise(0L);
        NumberExpression<Long> fourStar = new CaseBuilder()
                .when(reviewEntity.rating.eq(4)).then(1L)
                .otherwise(0L);
        NumberExpression<Long> threeStar = new CaseBuilder()
                .when(reviewEntity.rating.eq(3)).then(1L)
                .otherwise(0L);
        NumberExpression<Long> twoStar = new CaseBuilder()
                .when(reviewEntity.rating.eq(2)).then(1L)
                .otherwise(0L);
        NumberExpression<Long> oneStar = new CaseBuilder()
                .when(reviewEntity.rating.eq(1)).then(1L)
                .otherwise(0L);

        return qf.select(new QReviewDistribution(
                        fiveStar.sum(),
                        fourStar.sum(),
                        threeStar.sum(),
                        twoStar.sum(),
                        oneStar.sum()
                ))
                .from(reviewEntity)
                .where(reviewEntity.productEntity.id.eq(productId))
                .fetchOne();
    }

    @Override
    public List<Long> findHotTop10ProductIds() {
        return qf.select(reviewEntity.productEntity.id)
                .from(reviewEntity)
                .groupBy(reviewEntity.productEntity)
                .limit(10)
                .orderBy(
                        new OrderSpecifier<>(
                                Order.DESC,
                                reviewEntity.count().multiply(reviewEntity.rating.avg())
                        ))
                .fetch();
    }

    @Override
    public Page<ReviewEntity> findPage(ReviewQueryFilter reviewQueryFilter, Pageable pageable) {
        Long total = qf.select(reviewEntity.count())
                .from(reviewEntity)
                .where(reviewEntity.productEntity.id.eq(reviewQueryFilter.productId()))
                .fetchOne();

        if (total == null || total == 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<OrderSpecifier<?>> orderSpecifiers = getOrderSpecifiers(pageable.getSort());

        List<ReviewEntity> contents = qf.select(reviewEntity)
                .from(reviewEntity)
                .leftJoin(reviewEntity.userEntity, userEntity).fetchJoin()
                .where(
                        reviewEntity.productEntity.id.eq(reviewQueryFilter.productId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .fetch();

        return new PageImpl<>(contents, pageable, total);
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Sort sort) {
        PathBuilder<?> entityPath = new PathBuilder<>((Class<?>) ReviewEntity.class, "reviewEntity");
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        for (Sort.Order order : sort) {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();

            if ("created_at".equals(property)) {
                orders.add(new OrderSpecifier<>(direction, entityPath.getDateTime("createdAt", LocalDateTime.class)));
            } else {
                orders.add(new OrderSpecifier<>(direction, entityPath.getString(property)));
            }
        }

        return orders;
    }
}
