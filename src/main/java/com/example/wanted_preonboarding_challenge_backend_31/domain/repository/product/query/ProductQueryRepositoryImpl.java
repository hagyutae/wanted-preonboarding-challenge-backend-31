package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.query;

import static com.example.wanted_preonboarding_challenge_backend_31.domain.model.brand.QBrand.brand;
import static com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.QProduct.product;
import static com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.QProductCategory.productCategory;
import static com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.QProductImage.productImage;
import static com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.QProductPrice.productPrice;
import static com.example.wanted_preonboarding_challenge_backend_31.domain.model.seller.QSeller.seller;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.Product;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductStatus;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.QuerydslRepositorySupport;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.brand.BrandSearchDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationReq;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.pagination.PaginationRes;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductImageSearchDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.seller.SellerSearchDto;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.request.ProductSearchReq;
import com.example.wanted_preonboarding_challenge_backend_31.web.product.dto.response.ProductSearchDataRes;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ProductQueryRepositoryImpl extends QuerydslRepositorySupport implements ProductQueryRepository {

    protected ProductQueryRepositoryImpl() {
        super(Product.class);
    }

    @Override
    public List<ProductSearchDataRes> searchProducts(PaginationReq paginationReq, ProductSearchReq req) {
        Expression<Boolean> inStockSelect = new CaseBuilder()
                .when(product.status.eq(ProductStatus.OUT_OF_STOCK))
                .then(false)
                .otherwise(true);

        Expression<ProductSearchDataRes> select = Projections.constructor(
                ProductSearchDataRes.class,
                product.id,
                product.name,
                product.slug,
                product.shortDescription,
                productPrice.basePrice,
                productPrice.salePrice,
                productPrice.currency,
                Projections.constructor(
                        ProductImageSearchDto.class,
                        productImage.url,
                        productImage.altText
                ),
                Projections.constructor(
                        BrandSearchDto.class,
                        brand.id,
                        brand.name
                ),
                Projections.constructor(
                        SellerSearchDto.class,
                        seller.id,
                        seller.name
                ),
                Expressions.nullExpression(Double.class),
                Expressions.nullExpression(Long.class),
                inStockSelect,
                product.status,
                product.createdAt
        );

        return select(select)
                .from(product)
                .join(productPrice).on(productPrice.product.id.eq(product.id))
                .leftJoin(productImage).on(productImage.product.id.eq(product.id)
                        .and(productImage.isPrimary.eq(true))) // primary 이미지는 하나라고 가정
                .join(brand).on(brand.id.eq(product.brand.id))
                .join(seller).on(seller.id.eq(product.seller.id))
                .leftJoin(productCategory).on(productCategory.product.id.eq(product.id))
                .where(searchProductsConditions(req))
                .orderBy(createOrderSpecifier(req.sort()))
                .offset(paginationReq.getOffset())
                .limit(paginationReq.perPage())
                .fetch();
    }

    @Override
    public PaginationRes countSearchProducts(PaginationReq paginationReq, ProductSearchReq req) {
        Long totalItems = select(product.count())
                .from(product)
                .join(productPrice).on(productPrice.product.id.eq(product.id))
                .join(brand).on(brand.id.eq(product.brand.id))
                .join(seller).on(seller.id.eq(product.seller.id))
                .leftJoin(productCategory).on(productCategory.product.id.eq(product.id))
                .where(searchProductsConditions(req))
                .fetchOne();

        assert totalItems != null;
        return PaginationRes.of(totalItems.intValue(), paginationReq.page(), paginationReq.perPage());
    }

    /**
     * 필터
     */
    private BooleanExpression[] searchProductsConditions(ProductSearchReq req) {
        return new BooleanExpression[]{
                statusFilter(req.status()),
                priceFilter(req.minPrice(), req.maxPrice()),
                categoryFilter(req.category()),
                sellerFilter(req.seller()),
                brandFilter(req.brand()),
                inStockFilter(req.inStock()),
                searchFilter(req.search()),
        };
    }

    private BooleanExpression statusFilter(ProductStatus status) {
        return status == null ? null : product.status.eq(status);
    }

    private BooleanExpression priceFilter(Integer minPrice, Integer maxPrice) {
        if (minPrice == null && maxPrice == null) {
            return null;
        }
        if (maxPrice == null) {
            return productPrice.salePrice.goe(minPrice);
        }
        if (minPrice == null) {
            return productPrice.salePrice.loe(maxPrice);
        }
        return productPrice.salePrice.between(minPrice, maxPrice);
    }

    private BooleanExpression categoryFilter(List<Long> categories) {
        if (categories == null || categories.isEmpty()) {
            return null;
        }
        return productCategory.category.id.in(categories);
    }

    private BooleanExpression sellerFilter(Long sellerId) {
        return sellerId == null ? null : seller.id.eq(sellerId);
    }

    private BooleanExpression brandFilter(Long brandId) {
        return brandId == null ? null : brand.id.eq(brandId);
    }

    private BooleanExpression inStockFilter(Boolean inStock) {
        if (inStock == null) {
            return null;
        }
        return inStock.equals(Boolean.TRUE) ? product.status.ne(ProductStatus.OUT_OF_STOCK)
                : product.status.eq(ProductStatus.OUT_OF_STOCK);
    }

    private BooleanExpression searchFilter(String search) {
        if (search == null || search.isEmpty()) {
            return null;
        }
        return product.name.contains(search);
    }
}
