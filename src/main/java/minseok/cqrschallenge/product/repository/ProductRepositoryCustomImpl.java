package minseok.cqrschallenge.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import minseok.cqrschallenge.product.entity.Product;
import minseok.cqrschallenge.product.entity.ProductStatus;
import minseok.cqrschallenge.product.entity.QProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Product> findWithFilters(
        String status, Integer minPrice, Integer maxPrice,
        String category, Integer seller, Integer brand,
        Boolean inStock, String search, Pageable pageable) {

        QProduct product = QProduct.product;

        JPAQuery<Product> query = queryFactory
            .selectFrom(product)
            .where(product.status.ne(ProductStatus.DELETED));

        BooleanBuilder builder = new BooleanBuilder();

        if (status != null && !status.isEmpty()) {
            builder.and(product.status.eq(ProductStatus.valueOf(status)));
        }

        if (minPrice != null) {
            builder.and(product.price.basePrice.goe(minPrice));
        }
        if (maxPrice != null) {
            builder.and(product.price.basePrice.loe(maxPrice));
        }

        if (category != null && !category.isEmpty()) {
            String[] categoryIds = category.split(",");
            BooleanBuilder categoryBuilder = new BooleanBuilder();

            for (String id : categoryIds) {
                categoryBuilder.or(product.categories.any().category.id.eq(Long.parseLong(id)));
            }

            builder.and(categoryBuilder);
        }

        if (seller != null) {
            builder.and(product.seller.id.eq(seller.longValue()));
        }

        if (brand != null) {
            builder.and(product.brand.id.eq(brand.longValue()));
        }

        if (inStock != null && inStock) {
            builder.and(product.optionGroups.any().options.any().stock.gt(0));
        }

        if (search != null && !search.isEmpty()) {
            builder.and(
                product.name.containsIgnoreCase(search)
                    .or(product.shortDescription.containsIgnoreCase(search))
                    .or(product.fullDescription.containsIgnoreCase(search))
            );
        }

        query = query.where(builder);

        long total = query.fetchCount();

        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            for (Sort.Order order : sort) {
                PathBuilder<Product> pathBuilder = new PathBuilder<>(Product.class, "product");

                if (order.isAscending()) {
                    query = query.orderBy(pathBuilder.getString(order.getProperty()).asc());
                } else {
                    query = query.orderBy(pathBuilder.getString(order.getProperty()).desc());
                }
            }
        }

        query = query.offset(pageable.getOffset()).limit(pageable.getPageSize());

        List<Product> products = query.fetch();

        return new PageImpl<>(products, pageable, total);
    }
}