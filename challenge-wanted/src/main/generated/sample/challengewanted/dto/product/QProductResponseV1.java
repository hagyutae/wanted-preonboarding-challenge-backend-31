package sample.challengewanted.dto.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * sample.challengewanted.dto.product.QProductResponseV1 is a Querydsl Projection type for ProductResponseV1
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductResponseV1 extends ConstructorExpression<ProductResponseV1> {

    private static final long serialVersionUID = -550648611L;

    public QProductResponseV1(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> slug, com.querydsl.core.types.Expression<String> shortDescription, com.querydsl.core.types.Expression<String> fullDescription, com.querydsl.core.types.Expression<String> status, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> updatedAt, com.querydsl.core.types.Expression<? extends SellerResponse> seller, com.querydsl.core.types.Expression<? extends BrandResponse> brand, com.querydsl.core.types.Expression<? extends ProductDetailResponse> detail, com.querydsl.core.types.Expression<? extends ProductPriceResponse> price) {
        super(ProductResponseV1.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, SellerResponse.class, BrandResponse.class, ProductDetailResponse.class, ProductPriceResponse.class}, id, name, slug, shortDescription, fullDescription, status, createdAt, updatedAt, seller, brand, detail, price);
    }

    public QProductResponseV1(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> slug, com.querydsl.core.types.Expression<String> shortDescription, com.querydsl.core.types.Expression<String> fullDescription, com.querydsl.core.types.Expression<String> status, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt, com.querydsl.core.types.Expression<java.time.LocalDateTime> updatedAt, com.querydsl.core.types.Expression<? extends SellerResponse> seller, com.querydsl.core.types.Expression<? extends BrandResponse> brand, com.querydsl.core.types.Expression<? extends ProductDetailResponse> detail, com.querydsl.core.types.Expression<? extends ProductPriceResponse> price, com.querydsl.core.types.Expression<? extends java.util.List<ProductCategoryDto>> categories, com.querydsl.core.types.Expression<? extends java.util.List<sample.challengewanted.dto.option.OptionGroupResponse>> optionGroups, com.querydsl.core.types.Expression<? extends java.util.List<ImageResponse>> images, com.querydsl.core.types.Expression<? extends java.util.List<TagResponse>> tags, com.querydsl.core.types.Expression<? extends RatingDto> rating, com.querydsl.core.types.Expression<? extends java.util.List<RelatedProductDto>> relatedProducts) {
        super(ProductResponseV1.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, SellerResponse.class, BrandResponse.class, ProductDetailResponse.class, ProductPriceResponse.class, java.util.List.class, java.util.List.class, java.util.List.class, java.util.List.class, RatingDto.class, java.util.List.class}, id, name, slug, shortDescription, fullDescription, status, createdAt, updatedAt, seller, brand, detail, price, categories, optionGroups, images, tags, rating, relatedProducts);
    }

}

