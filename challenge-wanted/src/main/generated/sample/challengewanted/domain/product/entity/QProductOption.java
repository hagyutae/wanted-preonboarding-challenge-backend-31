package sample.challengewanted.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductOption is a Querydsl query type for ProductOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductOption extends EntityPathBase<ProductOption> {

    private static final long serialVersionUID = 6485634L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductOption productOption = new QProductOption("productOption");

    public final NumberPath<java.math.BigDecimal> additionalPrice = createNumber("additionalPrice", java.math.BigDecimal.class);

    public final NumberPath<Integer> displayOrder = createNumber("displayOrder", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<ProductImage, QProductImage> productImages = this.<ProductImage, QProductImage>createList("productImages", ProductImage.class, QProductImage.class, PathInits.DIRECT2);

    public final QProductOptionGroup productOptionGroup;

    public final StringPath sku = createString("sku");

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public QProductOption(String variable) {
        this(ProductOption.class, forVariable(variable), INITS);
    }

    public QProductOption(Path<? extends ProductOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductOption(PathMetadata metadata, PathInits inits) {
        this(ProductOption.class, metadata, inits);
    }

    public QProductOption(Class<? extends ProductOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productOptionGroup = inits.isInitialized("productOptionGroup") ? new QProductOptionGroup(forProperty("productOptionGroup"), inits.get("productOptionGroup")) : null;
    }

}

