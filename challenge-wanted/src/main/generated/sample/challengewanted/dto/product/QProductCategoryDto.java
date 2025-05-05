package sample.challengewanted.dto.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * sample.challengewanted.dto.product.QProductCategoryDto is a Querydsl Projection type for ProductCategoryDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductCategoryDto extends ConstructorExpression<ProductCategoryDto> {

    private static final long serialVersionUID = -200024608L;

    public QProductCategoryDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> slug, com.querydsl.core.types.Expression<Boolean> isPrimary, com.querydsl.core.types.Expression<ParentCategoryDto> parent) {
        super(ProductCategoryDto.class, new Class<?>[]{long.class, String.class, String.class, boolean.class, ParentCategoryDto.class}, id, name, slug, isPrimary, parent);
    }

}

