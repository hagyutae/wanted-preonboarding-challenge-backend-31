package com.mkhwang.wantedcqrs.product.infra.impl;

import com.mkhwang.wantedcqrs.common.querydsl.QuerydslUtil;
import com.mkhwang.wantedcqrs.config.advice.dto.ApiPageInfo;
import com.mkhwang.wantedcqrs.product.domain.QReview;
import com.mkhwang.wantedcqrs.product.domain.dto.ProductRatingDto;
import com.mkhwang.wantedcqrs.product.domain.dto.QReviewSearchItemDto;
import com.mkhwang.wantedcqrs.product.domain.dto.QReviewUserDto;
import com.mkhwang.wantedcqrs.product.domain.dto.review.*;
import com.mkhwang.wantedcqrs.product.infra.ReviewSearchRepository;
import com.mkhwang.wantedcqrs.user.domain.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReviewSearchRepositoryImpl implements ReviewSearchRepository {
  private final JPAQueryFactory jpaQueryFactory;
  private final QuerydslUtil querydslUtil;
  private final QReview qReview = QReview.review;
  private final QUser qUser = QUser.user;

  @Override
  public ProductRatingDto findAverageRatingByProductId(Long productId) {
    FlatRatingStatDto flatRating = jpaQueryFactory
            .select(
                    Projections.constructor(FlatRatingStatDto.class,
                            qReview.rating.avg(),
                            qReview.product.count().intValue().as("count"),
                            Expressions.numberTemplate(Integer.class, "SUM(CASE WHEN {0} = 1 THEN 1 ELSE 0 END)", qReview.rating),
                            Expressions.numberTemplate(Integer.class, "SUM(CASE WHEN {0} = 2 THEN 1 ELSE 0 END)", qReview.rating),
                            Expressions.numberTemplate(Integer.class, "SUM(CASE WHEN {0} = 3 THEN 1 ELSE 0 END)", qReview.rating),
                            Expressions.numberTemplate(Integer.class, "SUM(CASE WHEN {0} = 4 THEN 1 ELSE 0 END)", qReview.rating),
                            Expressions.numberTemplate(Integer.class, "SUM(CASE WHEN {0} = 5 THEN 1 ELSE 0 END)", qReview.rating)
                    )

            )
            .from(qReview)
            .where(qReview.product.id.eq(productId))
            .groupBy(qReview.product)
            .fetchOne();
    if (flatRating == null) {
      return null;
    }
    return new ProductRatingDto(
            flatRating.average(),
            flatRating.count(),
            Map.of(
                    "1", flatRating.rating1(),
                    "2", flatRating.rating2(),
                    "3", flatRating.rating3(),
                    "4", flatRating.rating4(),
                    "5", flatRating.rating5()
            )
    );
  }

  @Override
  public ReviewSearchResultDto findRatingByProductId(Long productId, ReviewSearchDto searchDto) {
    BooleanBuilder condition = this.generateCondition(productId, searchDto);
    Pageable pageable = searchDto.toPageable();

    long total = Optional.ofNullable(jpaQueryFactory
            .select(qReview.count())
            .from(qReview)
            .where(condition)
            .fetchOne()).orElse(0L);

    if (total == 0) {
      return ReviewSearchResultDto.of(
              List.of(),
              null,
              ApiPageInfo.empty(pageable)
      );
    }


    List<ReviewSearchItemDto> items = jpaQueryFactory.select(
                    new QReviewSearchItemDto(
                            qReview.id,
                            new QReviewUserDto(
                                    qUser.id,
                                    qUser.name,
                                    qUser.avatarUrl
                            ),
                            qReview.rating,
                            qReview.title,
                            qReview.content,
                            qReview.createdAt,
                            qReview.updatedAt,
                            qReview.verifiedPurchase,
                            qReview.helpfulVotes
                    ))
            .from(qReview)
            .join(qUser)
            .on(qUser.id.eq(qReview.user.id))
            .where(condition)
            .orderBy(querydslUtil.getOrderSpecifiers(pageable, qReview.getType(), qReview.getMetadata().getName()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    ReviewSearchSummaryDto summary = Optional.ofNullable(findAverageRatingByProductId(productId))
            .map(rating -> ReviewSearchSummaryDto.of(
                    rating.getAverage(),
                    rating.getCount(),
                    rating.getDistribution()
            ))
            .orElse(null);


    return ReviewSearchResultDto.of(
            items,
            summary,
            ApiPageInfo.of(
                    total,
                    (long) Math.ceil((double) total / pageable.getPageSize()),
                    pageable.getPageNumber() + 1,
                    pageable.getPageSize()
            )
    );
  }

  private BooleanBuilder generateCondition(Long productId, ReviewSearchDto searchDto) {
    BooleanBuilder condition = new BooleanBuilder();

    condition.and(qReview.product.id.eq(productId));
    if (searchDto.getRating() != null) {
      condition.and(qReview.rating.eq(searchDto.getRating()));
    }

    return condition;
  }
}
