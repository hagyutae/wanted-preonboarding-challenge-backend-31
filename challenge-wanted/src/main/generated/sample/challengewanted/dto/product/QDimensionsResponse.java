package sample.challengewanted.dto.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * sample.challengewanted.dto.product.QDimensionsResponse is a Querydsl Projection type for DimensionsResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QDimensionsResponse extends ConstructorExpression<DimensionsResponse> {

    private static final long serialVersionUID = 913273212L;

    public QDimensionsResponse(com.querydsl.core.types.Expression<Integer> width, com.querydsl.core.types.Expression<Integer> height, com.querydsl.core.types.Expression<Integer> depth) {
        super(DimensionsResponse.class, new Class<?>[]{int.class, int.class, int.class}, width, height, depth);
    }

}

