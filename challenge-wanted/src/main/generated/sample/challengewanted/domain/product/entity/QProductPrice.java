package sample.challengewanted.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductPrice is a Querydsl query type for ProductPrice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductPrice extends EntityPathBase<ProductPrice> {

    private static final long serialVersionUID = 1663749532L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductPrice productPrice = new QProductPrice("productPrice");

    public final NumberPath<java.math.BigDecimal> basePrice = createNumber("basePrice", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> costPrice = createNumber("costPrice", java.math.BigDecimal.class);

    public final StringPath currency = createString("currency");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProduct product;

    public final NumberPath<java.math.BigDecimal> salePrice = createNumber("salePrice", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> taxRate = createNumber("taxRate", java.math.BigDecimal.class);

    public QProductPrice(String variable) {
        this(ProductPrice.class, forVariable(variable), INITS);
    }

    public QProductPrice(Path<? extends ProductPrice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductPrice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductPrice(PathMetadata metadata, PathInits inits) {
        this(ProductPrice.class, metadata, inits);
    }

    public QProductPrice(Class<? extends ProductPrice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

