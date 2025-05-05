package sample.challengewanted.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductOptionGroup is a Querydsl query type for ProductOptionGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductOptionGroup extends EntityPathBase<ProductOptionGroup> {

    private static final long serialVersionUID = -1761947363L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductOptionGroup productOptionGroup = new QProductOptionGroup("productOptionGroup");

    public final NumberPath<Integer> displayOrder = createNumber("displayOrder", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QProduct product;

    public final QProductOption productOption;

    public final ListPath<ProductOption, QProductOption> productOptions = this.<ProductOption, QProductOption>createList("productOptions", ProductOption.class, QProductOption.class, PathInits.DIRECT2);

    public QProductOptionGroup(String variable) {
        this(ProductOptionGroup.class, forVariable(variable), INITS);
    }

    public QProductOptionGroup(Path<? extends ProductOptionGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductOptionGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductOptionGroup(PathMetadata metadata, PathInits inits) {
        this(ProductOptionGroup.class, metadata, inits);
    }

    public QProductOptionGroup(Class<? extends ProductOptionGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
        this.productOption = inits.isInitialized("productOption") ? new QProductOption(forProperty("productOption"), inits.get("productOption")) : null;
    }

}

