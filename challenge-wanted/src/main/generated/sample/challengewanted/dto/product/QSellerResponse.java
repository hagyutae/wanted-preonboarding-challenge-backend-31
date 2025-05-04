package sample.challengewanted.dto.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * sample.challengewanted.dto.product.QSellerResponse is a Querydsl Projection type for SellerResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QSellerResponse extends ConstructorExpression<SellerResponse> {

    private static final long serialVersionUID = -555350482L;

    public QSellerResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name) {
        super(SellerResponse.class, new Class<?>[]{long.class, String.class}, id, name);
    }

}

