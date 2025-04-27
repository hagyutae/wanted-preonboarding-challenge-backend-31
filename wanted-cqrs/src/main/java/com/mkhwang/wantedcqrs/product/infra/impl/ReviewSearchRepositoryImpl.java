package com.mkhwang.wantedcqrs.product.infra.impl;

import com.mkhwang.wantedcqrs.product.domain.QReview;
import com.mkhwang.wantedcqrs.product.domain.dto.FlatRatingStatDto;
import com.mkhwang.wantedcqrs.product.domain.dto.ProductRatingDto;
import com.mkhwang.wantedcqrs.product.infra.ReviewSearchRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReviewSearchRepositoryImpl implements ReviewSearchRepository {
  private final JPAQueryFactory jpaQueryFactory;
  private final QReview qReview = QReview.review;

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
}
