package sample.challengewanted.dto.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * sample.challengewanted.dto.product.QImageResponse is a Querydsl Projection type for ImageResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QImageResponse extends ConstructorExpression<ImageResponse> {

    private static final long serialVersionUID = 1151567790L;

    public QImageResponse(com.querydsl.core.types.Expression<String> url, com.querydsl.core.types.Expression<String> altText) {
        super(ImageResponse.class, new Class<?>[]{String.class, String.class}, url, altText);
    }

    public QImageResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> url, com.querydsl.core.types.Expression<String> altText, com.querydsl.core.types.Expression<Boolean> isPrimary, com.querydsl.core.types.Expression<Integer> displayOrder, com.querydsl.core.types.Expression<Long> optionId) {
        super(ImageResponse.class, new Class<?>[]{long.class, String.class, String.class, boolean.class, int.class, long.class}, id, url, altText, isPrimary, displayOrder, optionId);
    }

}

