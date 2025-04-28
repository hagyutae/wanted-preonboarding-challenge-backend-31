package sample.challengewanted.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductDetail is a Querydsl query type for ProductDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductDetail extends EntityPathBase<ProductDetail> {

    private static final long serialVersionUID = -318601634L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductDetail productDetail = new QProductDetail("productDetail");

    public final StringPath additionalInfo = createString("additionalInfo");

    public final StringPath careInstructions = createString("careInstructions");

    public final StringPath countryOfOrigin = createString("countryOfOrigin");

    public final StringPath dimensions = createString("dimensions");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath material = createString("material");

    public final QProduct product;

    public final ListPath<sample.challengewanted.domain.review.Review, sample.challengewanted.domain.review.QReview> reviews = this.<sample.challengewanted.domain.review.Review, sample.challengewanted.domain.review.QReview>createList("reviews", sample.challengewanted.domain.review.Review.class, sample.challengewanted.domain.review.QReview.class, PathInits.DIRECT2);

    public final StringPath warrantyInfo = createString("warrantyInfo");

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QProductDetail(String variable) {
        this(ProductDetail.class, forVariable(variable), INITS);
    }

    public QProductDetail(Path<? extends ProductDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductDetail(PathMetadata metadata, PathInits inits) {
        this(ProductDetail.class, metadata, inits);
    }

    public QProductDetail(Class<? extends ProductDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

