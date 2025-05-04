package sample.challengewanted.dto.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * sample.challengewanted.dto.product.QBrandResponse is a Querydsl Projection type for BrandResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBrandResponse extends ConstructorExpression<BrandResponse> {

    private static final long serialVersionUID = -12304934L;

    public QBrandResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name) {
        super(BrandResponse.class, new Class<?>[]{long.class, String.class}, id, name);
    }

}

