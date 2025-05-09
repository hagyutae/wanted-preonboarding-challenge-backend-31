package com.wanted_preonboarding_challenge_backend.eCommerce.respository;

import com.wanted_preonboarding_challenge_backend.eCommerce.domain.Product;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.ProductSummaryDto;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request.ProductsByCategoryCondition;
import com.wanted_preonboarding_challenge_backend.eCommerce.dto.product.request.ProductsSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ProductRepositoryImpl implements ProductRepositoryCustom{

    @Override
    public Page<ProductSummaryDto> findByCondition(ProductsSearchCondition condition, Pageable pageable) {
//        QProduct p = QProduct.product;
//        QBrand b = QBrand.brand;
//        QSeller s = QSeller.seller;
//
//        List<ProductListItemDto> content = queryFactory
//                .select(Projections.constructor(ProductListItemDto.class,
//                        p.id, p.name, p.slug, p.shortDescription,
//                        p.price.salePrice, p.price.basePrice, p.price.currency,
//                        p.primaryImage.url, p.primaryImage.altText,
//                        b.id, b.name,
//                        s.id, s.name,
//                        p.review.ratingAvg, p.review.reviewCount,
//                        p.status, p.createdAt
//                ))
//                .from(p)
//                .leftJoin(p.brand, b)
//                .leftJoin(p.seller, s)
//                .where(
//                        p.status.eq(cond.getStatus()),
//                        p.price.salePrice.between(cond.getMinPrice(), cond.getMaxPrice()),
//                        // ...
//                )
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .orderBy(p.createdAt.desc())
//                .fetch();
//
//        long total = queryFactory
//                .select(p.count())
//                .from(p)
//                .where( /* 같은 조건 */ )
//                .fetchOne();
//
//        return new PageImpl<>(content, pageable, total);
        return null;
    }

    @Override
    public Page<ProductSummaryDto> findByCondition(ProductsByCategoryCondition condition, Pageable pageable) {
        return null;
    }


    @Override
    public Product findWithSellerBrandPriceDetailById(Long productId) {
//        QProduct product = QProduct.product;
//        QProductPrice price = QProductPrice.productPrice;
//        QProductDetail detail = QProductDetail.productDetail;
//        QSeller seller = QSeller.seller;
//        QBrand brand = QBrand.brand;
//
//        Product result = queryFactory
//                .selectFrom(product)
//                .join(product.seller, seller).fetchJoin()
//                .join(product.brand, brand).fetchJoin()
//                .join(product.price, price).fetchJoin()
//                .join(product.detail, detail).fetchJoin()
//                .where(product.id.eq(productId))
//                .fetchOne();
//
//        return result;
        return null;
    }


//    private OrderSpecifier<?> getSortSpecifier(String sort, QProduct product) {
//        if (sort == null || sort.isBlank()) {
//            return product.createdAt.desc();
//        }
//
//        String[] parts = sort.split(":");
//        String field = parts[0];
//        String order = parts.length > 1 ? parts[1] : "desc";
//
//        switch (field) {
//            case "name":
//                return "asc".equals(order) ? product.name.asc() : product.name.desc();
//            case "created_at":
//                return "asc".equals(order) ? product.createdAt.asc() : product.createdAt.desc();
//            default:
//                return product.createdAt.desc();
//        }
//    }
}
