package sample.challengewanted.domain.seller;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSeller is a Querydsl query type for Seller
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeller extends EntityPathBase<Seller> {

    private static final long serialVersionUID = 226590372L;

    public static final QSeller seller = new QSeller("seller");

    public final StringPath contactEmail = createString("contactEmail");

    public final StringPath contactPhone = createString("contactPhone");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath logoUrl = createString("logoUrl");

    public final StringPath name = createString("name");

    public final ListPath<sample.challengewanted.domain.product.entity.Product, sample.challengewanted.domain.product.entity.QProduct> products = this.<sample.challengewanted.domain.product.entity.Product, sample.challengewanted.domain.product.entity.QProduct>createList("products", sample.challengewanted.domain.product.entity.Product.class, sample.challengewanted.domain.product.entity.QProduct.class, PathInits.DIRECT2);

    public final NumberPath<Double> rating = createNumber("rating", Double.class);

    public QSeller(String variable) {
        super(Seller.class, forVariable(variable));
    }

    public QSeller(Path<? extends Seller> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSeller(PathMetadata metadata) {
        super(Seller.class, metadata);
    }

}

