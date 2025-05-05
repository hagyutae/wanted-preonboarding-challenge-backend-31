package sample.challengewanted.dto.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * sample.challengewanted.dto.product.QTagResponse is a Querydsl Projection type for TagResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QTagResponse extends ConstructorExpression<TagResponse> {

    private static final long serialVersionUID = 1712817005L;

    public QTagResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> slug) {
        super(TagResponse.class, new Class<?>[]{long.class, String.class, String.class}, id, name, slug);
    }

}

