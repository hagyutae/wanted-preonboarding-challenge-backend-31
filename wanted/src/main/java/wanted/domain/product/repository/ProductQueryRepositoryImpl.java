package wanted.domain.product.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import wanted.domain.product.dto.ProductSearchCondition;
import wanted.domain.product.entity.Product;
import wanted.domain.product.entity.QProduct;
import wanted.domain.product.entity.QProductCategory;
import wanted.domain.product.entity.QProductPrice;
import wanted.domain.product.entity.Status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Product> search(ProductSearchCondition condition, Pageable pageable) {
        QProduct product = QProduct.product;
        QProductPrice price = QProductPrice.productPrice;
        QProductCategory category = QProductCategory.productCategory;

        List<Product> content = queryFactory
                .selectFrom(product)
                .leftJoin(product.productPrice, price).fetchJoin()
                .leftJoin(product.productCategories, category).fetchJoin()
                .where(
                        eqStatus(condition.status()),
                        priceBetween(condition.minPrice(), condition.maxPrice()),
                        bySeller(condition.seller()),
                        byBrand(condition.brand()),
                        categoryIn(condition.category()),
                        nameOrDescContains(condition.search())
                )
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(toOrderSpecifier(condition.sort()))
                .fetch();

        Long total = queryFactory
                .select(product.countDistinct())
                .from(product)
                .leftJoin(product.productPrice, price)
                .leftJoin(product.productCategories, category)
                .where(
                        eqStatus(condition.status()),
                        priceBetween(condition.minPrice(), condition.maxPrice()),
                        bySeller(condition.seller()),
                        byBrand(condition.brand()),
                        categoryIn(condition.category()),
                        nameOrDescContains(condition.search())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    private BooleanExpression eqStatus(String status) {
        return status != null ? QProduct.product.status.eq(Status.valueOf(status)) : null;
    }

    private BooleanExpression priceBetween(Integer min, Integer max) {
        QProductPrice price = QProductPrice.productPrice;
        if (min != null && max != null) return price.basePrice.between(BigDecimal.valueOf(min), BigDecimal.valueOf(max));
        if (min != null) return price.basePrice.goe(BigDecimal.valueOf(min));
        if (max != null) return price.basePrice.loe(BigDecimal.valueOf(max));
        return null;
    }

    private BooleanExpression bySeller(Integer sellerId) {
        return sellerId != null ? QProduct.product.seller.id.eq(Long.valueOf(sellerId)) : null;
    }

    private BooleanExpression byBrand(Integer brandId) {
        return brandId != null ? QProduct.product.brand.id.eq(Long.valueOf(brandId)) : null;
    }

    private BooleanExpression categoryIn(List<Integer> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) return null;
        return QProductCategory.productCategory.category.id.in(categoryIds.stream().map(Long::valueOf).toList());
    }

    private BooleanExpression nameOrDescContains(String keyword) {
        if (keyword == null || keyword.isBlank()) return null;
        return QProduct.product.name.containsIgnoreCase(keyword)
                .or(QProduct.product.shortDescription.containsIgnoreCase(keyword));
    }

    private OrderSpecifier<?>[] toOrderSpecifier(String sortString) {
        QProduct product = QProduct.product;

        if (sortString == null || sortString.isBlank()) {
            return new OrderSpecifier[]{product.createdAt.desc()};
        }

        return Arrays.stream(sortString.split(","))
                .map(s -> {
                    String[] parts = s.split(":");
                    String field = parts[0];
                    boolean asc = parts.length < 2 || parts[1].equalsIgnoreCase("asc");

                    return switch (field) {
                        case "createdAt" -> asc ? product.createdAt.asc() : product.createdAt.desc();
                        case "name" -> asc ? product.name.asc() : product.name.desc();
                        case "basePrice" -> {
                            QProductPrice price = QProductPrice.productPrice;
                            yield asc ? price.basePrice.asc() : price.basePrice.desc();
                        }
                        default -> product.createdAt.desc(); // fallback
                    };
                })
                .toArray(OrderSpecifier[]::new);
    }

}
