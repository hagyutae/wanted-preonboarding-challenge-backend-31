package sample.challengewanted.dto.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * sample.challengewanted.dto.product.QProductDetailResponse is a Querydsl Projection type for ProductDetailResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductDetailResponse extends ConstructorExpression<ProductDetailResponse> {

    private static final long serialVersionUID = 971004275L;

    public QProductDetailResponse(com.querydsl.core.types.Expression<Double> weight, com.querydsl.core.types.Expression<String> dimensionsJson, com.querydsl.core.types.Expression<String> materials, com.querydsl.core.types.Expression<String> countryOfOrigin, com.querydsl.core.types.Expression<String> warrantyInfo, com.querydsl.core.types.Expression<String> careInstructions, com.querydsl.core.types.Expression<String> additionalInfoJson) {
        super(ProductDetailResponse.class, new Class<?>[]{double.class, String.class, String.class, String.class, String.class, String.class, String.class}, weight, dimensionsJson, materials, countryOfOrigin, warrantyInfo, careInstructions, additionalInfoJson);
    }

}

