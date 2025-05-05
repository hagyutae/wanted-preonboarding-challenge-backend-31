package sample.challengewanted.dto.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * sample.challengewanted.dto.product.QRelatedProductDto is a Querydsl Projection type for RelatedProductDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QRelatedProductDto extends ConstructorExpression<RelatedProductDto> {

    private static final long serialVersionUID = -1178858259L;

    public QRelatedProductDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> shortDescription, com.querydsl.core.types.Expression<? extends ImageResponse> primaryImage, com.querydsl.core.types.Expression<? extends java.math.BigDecimal> basePrice, com.querydsl.core.types.Expression<? extends java.math.BigDecimal> salePrice, com.querydsl.core.types.Expression<String> currency) {
        super(RelatedProductDto.class, new Class<?>[]{long.class, String.class, String.class, ImageResponse.class, java.math.BigDecimal.class, java.math.BigDecimal.class, String.class}, id, name, shortDescription, primaryImage, basePrice, salePrice, currency);
    }

}

