package minseok.cqrschallenge.product.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import minseok.cqrschallenge.product.entity.Product;
import minseok.cqrschallenge.product.entity.ProductStatus;
import minseok.cqrschallenge.product.entity.QProduct;
import minseok.cqrschallenge.product.entity.QProductCategory;
import minseok.cqrschallenge.product.entity.QProductOption;
import minseok.cqrschallenge.product.entity.QProductOptionGroup;
import minseok.cqrschallenge.product.entity.QProductPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@Slf4j
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

        JPAQuery<Product> query = createBaseQuery(product);

        BooleanExpression filterCondition = buildFilterCondition(
            product, status, category, seller, brand, inStock, search, minPrice, maxPrice);

        boolean hasPriceFilter = isPriceFilterPresent(minPrice, maxPrice);
        query = applyPriceJoin(query, product, hasPriceFilter, minPrice, maxPrice);

        query = query.where(filterCondition);
        long total = countTotalProducts(product, filterCondition, hasPriceFilter);

        query = applySorting(query, pageable.getSort(), product);
        query = applyPaging(query, pageable);

        List<Product> products = query.fetch();
        log.debug("Found {} products with filter conditions", products.size());

        return new PageImpl<>(products, pageable, total);
    }

    private JPAQuery<Product> createBaseQuery(QProduct product) {
        return queryFactory.selectFrom(product)
            .leftJoin(product.seller).fetchJoin()
            .leftJoin(product.brand).fetchJoin();
    }

    private BooleanExpression buildFilterCondition(
        QProduct product, String status, String category,
        Integer seller, Integer brand, Boolean inStock,
        String search, Integer minPrice, Integer maxPrice) {

        BooleanExpression condition = product.status.ne(ProductStatus.DELETED);

        condition = applyStatusFilter(condition, product, status);
        condition = applyCategoryFilter(condition, product, category);
        condition = applySellerFilter(condition, product, seller);
        condition = applyBrandFilter(condition, product, brand);
        condition = applyStockFilter(condition, product, inStock);
        condition = applySearchFilter(condition, product, search);

        return condition;
    }

    private BooleanExpression applyStatusFilter(BooleanExpression condition, QProduct product, String status) {
        if (!StringUtils.hasText(status)) {
            return condition;
        }

            ProductStatus statusEnum = ProductStatus.valueOf(status);
            return condition.and(product.status.eq(statusEnum));

    }

    private BooleanExpression applyCategoryFilter(BooleanExpression condition, QProduct product, String category) {
        if (!StringUtils.hasText(category)) {
            return condition;
        }

        Optional<BooleanExpression> categoryCondition = parseCategory(product, category);
        return categoryCondition.map(condition::and).orElse(condition);
    }

    private Optional<BooleanExpression> parseCategory(QProduct product, String categoryParam) {
        try {
            Long categoryId = Long.parseLong(categoryParam);
            QProductCategory productCategory = QProductCategory.productCategory;

            return Optional.of(JPAExpressions
                .selectOne()
                .from(productCategory)
                .where(productCategory.product.eq(product)
                    .and(productCategory.category.id.eq(categoryId)))
                .exists());
        } catch (NumberFormatException e) {
            log.warn("Invalid category ID format: {}", categoryParam);
            return Optional.empty();
        }
    }

    private BooleanExpression applySellerFilter(BooleanExpression condition, QProduct product, Integer seller) {
        if (seller == null || seller <= 0) {
            return condition;
        }
        return condition.and(product.seller.id.eq(seller.longValue()));
    }

    private BooleanExpression applyBrandFilter(BooleanExpression condition, QProduct product, Integer brand) {
        if (brand == null || brand <= 0) {
            return condition;
        }
        return condition.and(product.brand.id.eq(brand.longValue()));
    }

    private BooleanExpression applyStockFilter(BooleanExpression condition, QProduct product, Boolean inStock) {
        if (!Boolean.TRUE.equals(inStock)) {
            return condition;
        }

        QProductOptionGroup optionGroup = QProductOptionGroup.productOptionGroup;
        QProductOption option = QProductOption.productOption;

        return condition.and(JPAExpressions
            .selectOne()
            .from(optionGroup)
            .join(optionGroup.options, option)
            .where(optionGroup.product.eq(product)
                .and(option.stock.gt(0)))
            .exists());
    }

    private BooleanExpression applySearchFilter(BooleanExpression condition, QProduct product, String search) {
        if (!StringUtils.hasText(search)) {
            return condition;
        }

        String pattern = "%" + search.toLowerCase() + "%";
        return condition.and(product.name.lower().like(pattern)
            .or(product.shortDescription.lower().like(pattern))
            .or(product.fullDescription.lower().like(pattern)));
    }

    private boolean isPriceFilterPresent(Integer minPrice, Integer maxPrice) {
        return (minPrice != null && minPrice > 0) || (maxPrice != null && maxPrice > 0);
    }

    private JPAQuery<Product> applyPriceJoin(JPAQuery<Product> query, QProduct product, boolean hasPriceFilter,
        Integer minPrice, Integer maxPrice) {
        if (hasPriceFilter) {
            QProductPrice price = QProductPrice.productPrice;
            BooleanExpression priceCondition = buildPriceCondition(price, minPrice, maxPrice);
            return query.join(product.price, price)
                .where(priceCondition);
        } else {
            return query.leftJoin(product.price).fetchJoin();
        }
    }

    private BooleanExpression buildPriceCondition(QProductPrice price, Integer minPrice, Integer maxPrice) {
        BooleanExpression condition = null;

        if (minPrice != null && minPrice > 0) {
            condition = price.basePrice.goe(minPrice);
        }

        if (maxPrice != null && maxPrice > 0) {
            BooleanExpression maxPriceCondition = price.basePrice.loe(maxPrice);
            condition = condition == null ? maxPriceCondition : condition.and(maxPriceCondition);
        }

        return condition;
    }

    private long countTotalProducts(QProduct product, BooleanExpression filterCondition, boolean hasPriceFilter) {
        JPAQuery<Long> countQuery = queryFactory.select(product.count())
            .from(product);

        if (hasPriceFilter) {
            QProductPrice price = QProductPrice.productPrice;
            countQuery = countQuery.join(product.price, price);
        }

        countQuery = countQuery.where(filterCondition);
        return Optional.ofNullable(countQuery.fetchOne()).orElse(0L);
    }

    private JPAQuery<Product> applySorting(JPAQuery<Product> query, Sort sort, QProduct product) {
        if (sort.isEmpty()) {
            return query.orderBy(product.createdAt.desc());
        }

        for (Sort.Order order : sort) {
            OrderSpecifier<?> orderSpecifier = createOrderSpecifier(order, product);
            query = query.orderBy(orderSpecifier);
        }

        return query;
    }

    private OrderSpecifier<?> createOrderSpecifier(Sort.Order order, QProduct product) {
        String property = order.getProperty();
        Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

        return switch (property) {
            case "id" -> new OrderSpecifier<>(direction, product.id);
            case "created_at" -> new OrderSpecifier<>(direction, product.createdAt);
            case "updated_at" -> new OrderSpecifier<>(direction, product.updatedAt);
            case "name" -> new OrderSpecifier<>(direction, product.name);
            case "base_price" -> {
                QProductPrice price = QProductPrice.productPrice;
                yield new OrderSpecifier<>(direction, price.basePrice);
            }
            case "sale_price" -> {
                QProductPrice price = QProductPrice.productPrice;
                yield new OrderSpecifier<>(direction, price.salePrice);
            }
            default -> new OrderSpecifier<>(Order.DESC, product.createdAt);
        };
    }

    private JPAQuery<Product> applyPaging(JPAQuery<Product> query, Pageable pageable) {
        return query.offset(pageable.getOffset())
            .limit(pageable.getPageSize());
    }
}