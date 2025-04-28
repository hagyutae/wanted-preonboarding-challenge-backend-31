package sample.challengewanted.domain.brand;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBrand is a Querydsl query type for Brand
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBrand extends EntityPathBase<Brand> {

    private static final long serialVersionUID = 568856950L;

    public static final QBrand brand = new QBrand("brand");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath logoUrl = createString("logoUrl");

    public final StringPath name = createString("name");

    public final ListPath<sample.challengewanted.domain.product.entity.Product, sample.challengewanted.domain.product.entity.QProduct> products = this.<sample.challengewanted.domain.product.entity.Product, sample.challengewanted.domain.product.entity.QProduct>createList("products", sample.challengewanted.domain.product.entity.Product.class, sample.challengewanted.domain.product.entity.QProduct.class, PathInits.DIRECT2);

    public final StringPath slug = createString("slug");

    public final StringPath website = createString("website");

    public QBrand(String variable) {
        super(Brand.class, forVariable(variable));
    }

    public QBrand(Path<? extends Brand> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBrand(PathMetadata metadata) {
        super(Brand.class, metadata);
    }

}

