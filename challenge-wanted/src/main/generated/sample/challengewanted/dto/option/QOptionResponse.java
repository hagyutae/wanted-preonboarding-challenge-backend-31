package sample.challengewanted.dto.option;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * sample.challengewanted.dto.option.QOptionResponse is a Querydsl Projection type for OptionResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QOptionResponse extends ConstructorExpression<OptionResponse> {

    private static final long serialVersionUID = -1468004768L;

    public QOptionResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<? extends java.math.BigDecimal> additionalPrice, com.querydsl.core.types.Expression<String> sku, com.querydsl.core.types.Expression<Integer> stock, com.querydsl.core.types.Expression<Integer> displayOrder) {
        super(OptionResponse.class, new Class<?>[]{long.class, String.class, java.math.BigDecimal.class, String.class, int.class, int.class}, id, name, additionalPrice, sku, stock, displayOrder);
    }

}

