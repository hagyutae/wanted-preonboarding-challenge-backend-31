package sample.challengewanted.dto.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * sample.challengewanted.dto.product.QAdditionalInfoResponse is a Querydsl Projection type for AdditionalInfoResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAdditionalInfoResponse extends ConstructorExpression<AdditionalInfoResponse> {

    private static final long serialVersionUID = -1492215772L;

    public QAdditionalInfoResponse(com.querydsl.core.types.Expression<Boolean> assemblyRequired, com.querydsl.core.types.Expression<String> assemblyTime) {
        super(AdditionalInfoResponse.class, new Class<?>[]{boolean.class, String.class}, assemblyRequired, assemblyTime);
    }

}

