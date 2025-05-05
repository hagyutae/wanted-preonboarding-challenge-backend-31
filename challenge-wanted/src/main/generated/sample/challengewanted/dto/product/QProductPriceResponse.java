package sample.challengewanted.dto.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * sample.challengewanted.dto.product.QProductPriceResponse is a Querydsl Projection type for ProductPriceResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductPriceResponse extends ConstructorExpression<ProductPriceResponse> {

    private static final long serialVersionUID = -351180087L;

    public QProductPriceResponse(com.querydsl.core.types.Expression<? extends java.math.BigDecimal> basePrice, com.querydsl.core.types.Expression<? extends java.math.BigDecimal> salePrice, com.querydsl.core.types.Expression<String> currency, com.querydsl.core.types.Expression<? extends java.math.BigDecimal> taxRate) {
        super(ProductPriceResponse.class, new Class<?>[]{java.math.BigDecimal.class, java.math.BigDecimal.class, String.class, java.math.BigDecimal.class}, basePrice, salePrice, currency, taxRate);
    }

}

