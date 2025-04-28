package sample.challengewanted.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 952285101L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final sample.challengewanted.domain.QBaseEntity _super = new sample.challengewanted.domain.QBaseEntity(this);

    public final sample.challengewanted.domain.brand.QBrand brand;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath fullDescription = createString("fullDescription");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QProductPrice price;

    public final ListPath<sample.challengewanted.domain.category.ProductCategory, sample.challengewanted.domain.category.QProductCategory> productCategories = this.<sample.challengewanted.domain.category.ProductCategory, sample.challengewanted.domain.category.QProductCategory>createList("productCategories", sample.challengewanted.domain.category.ProductCategory.class, sample.challengewanted.domain.category.QProductCategory.class, PathInits.DIRECT2);

    public final ListPath<ProductOptionGroup, QProductOptionGroup> productOptionGroups = this.<ProductOptionGroup, QProductOptionGroup>createList("productOptionGroups", ProductOptionGroup.class, QProductOptionGroup.class, PathInits.DIRECT2);

    public final ListPath<sample.challengewanted.domain.tag.ProductTag, sample.challengewanted.domain.tag.QProductTag> productTags = this.<sample.challengewanted.domain.tag.ProductTag, sample.challengewanted.domain.tag.QProductTag>createList("productTags", sample.challengewanted.domain.tag.ProductTag.class, sample.challengewanted.domain.tag.QProductTag.class, PathInits.DIRECT2);

    public final sample.challengewanted.domain.seller.QSeller seller;

    public final StringPath shortDescription = createString("shortDescription");

    public final StringPath slug = createString("slug");

    public final StringPath status = createString("status");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.brand = inits.isInitialized("brand") ? new sample.challengewanted.domain.brand.QBrand(forProperty("brand")) : null;
        this.price = inits.isInitialized("price") ? new QProductPrice(forProperty("price"), inits.get("price")) : null;
        this.seller = inits.isInitialized("seller") ? new sample.challengewanted.domain.seller.QSeller(forProperty("seller")) : null;
    }

}

