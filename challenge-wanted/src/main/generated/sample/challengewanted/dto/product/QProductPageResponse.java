package sample.challengewanted.dto.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * sample.challengewanted.dto.product.QProductPageResponse is a Querydsl Projection type for ProductPageResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductPageResponse extends ConstructorExpression<ProductPageResponse> {

    private static final long serialVersionUID = -175832591L;

    public QProductPageResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> slug, com.querydsl.core.types.Expression<String> shortDescription, com.querydsl.core.types.Expression<? extends java.math.BigDecimal> basePrice, com.querydsl.core.types.Expression<? extends java.math.BigDecimal> salePrice, com.querydsl.core.types.Expression<String> currency, com.querydsl.core.types.Expression<? extends ImageResponse> primaryImage, com.querydsl.core.types.Expression<? extends BrandResponse> brand, com.querydsl.core.types.Expression<? extends SellerResponse> seller, com.querydsl.core.types.Expression<Double> rating, com.querydsl.core.types.Expression<Long> reviewCount, com.querydsl.core.types.Expression<Integer> inStock, com.querydsl.core.types.Expression<String> status, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt) {
        super(ProductPageResponse.class, new Class<?>[]{long.class, String.class, String.class, String.class, java.math.BigDecimal.class, java.math.BigDecimal.class, String.class, ImageResponse.class, BrandResponse.class, SellerResponse.class, double.class, long.class, int.class, String.class, java.time.LocalDateTime.class}, id, name, slug, shortDescription, basePrice, salePrice, currency, primaryImage, brand, seller, rating, reviewCount, inStock, status, createdAt);
    }

}

