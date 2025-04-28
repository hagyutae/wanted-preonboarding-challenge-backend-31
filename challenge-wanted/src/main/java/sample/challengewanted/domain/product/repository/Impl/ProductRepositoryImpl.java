package sample.challengewanted.domain.product.repository.Impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sample.challengewanted.api.controller.product.response.ProductResponse;
import sample.challengewanted.domain.product.entity.QProduct;
import sample.challengewanted.domain.product.repository.ProductRepositoryCustom;
import sample.challengewanted.dto.ProductSearchCondition;

import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductResponse> searchProducts(ProductSearchCondition condition, Pageable pageable) {
        // Q타입
        QProduct product = QProduct.product;

        // 본문 조회
        List<ProductResponse> content = queryFactory
                .select(Projections.constructor(ProductResponse.class,
                        product.id,
                        product.name,
                        product.slug,
                        product.createdAt,
                        product.updatedAt
                ))
                .from(product)
                .where(
                        eqStatus(condition.getStatus()),
                        betweenPrice(condition.getMinPrice(), condition.getMaxPrice()),
                        containsName(condition.getSearch()),
                        eqBrand(condition.getBrand()),
                        eqSeller(condition.getSeller())
//                        eqInStock(condition.getInStock())
                        // 카테고리 조인 필요시 추가 가능
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(product.createdAt.desc())  // 기본 정렬 예시
                .fetch();

        // total count 조회
        Long total = queryFactory
                .select(product.count())
                .from(product)
                .where(
                        eqStatus(condition.getStatus()),
                        betweenPrice(condition.getMinPrice(), condition.getMaxPrice()),
                        containsName(condition.getSearch()),
                        eqBrand(condition.getBrand()),
                        eqSeller(condition.getSeller())
//                        eqInStock(condition.getInStock())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }

    // ====== 동적 where 조건 ======
    private BooleanExpression eqStatus(String status) {
        return status != null ? QProduct.product.status.eq(status) : null;
    }

    private BooleanExpression betweenPrice(Integer minPrice, Integer maxPrice) {
        if (minPrice != null && maxPrice != null) {
            return QProduct.product.price.basePrice.between(minPrice, maxPrice);
        } else if (minPrice != null) {
            return QProduct.product.price.basePrice.goe(minPrice);
        } else if (maxPrice != null) {
            return QProduct.product.price.basePrice.loe(maxPrice);
        } else {
            return null;
        }
    }

    private BooleanExpression containsName(String search) {
        return search != null ? QProduct.product.name.containsIgnoreCase(search) : null;
    }

    private BooleanExpression eqBrand(Integer brandId) {
        return brandId != null ? QProduct.product.brand.id.eq(Long.valueOf(brandId)) : null;
    }

    private BooleanExpression eqSeller(Integer sellerId) {
        return sellerId != null ? QProduct.product.seller.id.eq(Long.valueOf(sellerId)) : null;
    }

//    private BooleanExpression eqInStock(Boolean inStock) {
//        return inStock != null ? QProduct.product.eq(inStock) : null;
//    }
}
