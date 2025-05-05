package sample.challengewanted.dto.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * sample.challengewanted.dto.product.QRatingDto is a Querydsl Projection type for RatingDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRatingDto extends ConstructorExpression<RatingDto> {

    private static final long serialVersionUID = -1158130444L;

    public QRatingDto(com.querydsl.core.types.Expression<Double> average, com.querydsl.core.types.Expression<Integer> count, com.querydsl.core.types.Expression<? extends java.util.List<java.util.Map<String, Long>>> distribution) {
        super(RatingDto.class, new Class<?>[]{double.class, int.class, java.util.List.class}, average, count, distribution);
    }

}

