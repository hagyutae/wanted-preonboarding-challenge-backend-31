package com.example.cqrsapp.main.repository;

import com.example.cqrsapp.main.dto.CategoryDto;
import com.example.cqrsapp.main.dto.ProductDto;
import com.example.cqrsapp.product.domain.*;
import com.example.cqrsapp.review.domain.QReview;
import com.example.cqrsapp.seller.domain.QSeller;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MainRepositoryImpl implements MainRepository {

    private final QProduct product = QProduct.product;
    private final QProductImage productImage = QProductImage.productImage;
    private final QProductPrice productPrice = QProductPrice.productPrice;
    private final QProductOption option = QProductOption.productOption;
    private final QBrand brand = QBrand.brand;
    private final QSeller seller = QSeller.seller;
    private final QReview review = QReview.review;
    private final QCategory category = QCategory.category;
    private final QProductCategory productCategory = QProductCategory.productCategory;
    private final QProductOptionGroup productOptionGroup = QProductOptionGroup.productOptionGroup;


    private final EntityManager em;
    private JPAQueryFactory queryFactory;

    public MainRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ProductDto> findNewProducts() {
        return queryFactory
                .select(Projections.constructor(ProductDto.class,
                        product.id,
                        product.name,
                        product.slug,
                        product.shortDescription,
                        productPrice.basePrice,
                        productPrice.salePrice,
                        productPrice.currency,
                        productImage.url,
                        productImage.altText,
                        brand.id,
                        brand.name,
                        seller.id,
                        seller.name,
                        // 서브쿼리들
                        queryFactory.select(review.rating.avg())
                                .from(review)
                                .where(review.product.id.eq(product.id)),
                        queryFactory.select(review.count())
                                .from(review)
                                .where(review.product.id.eq(product.id)),
                        queryFactory.select(option.stock.sum().gt(0))
                                .from(option)
                                .innerJoin(option.optionGroup, productOptionGroup)
                                .where(productOptionGroup.product.id.eq(product.id)),
                        product.status,
                        product.createdAt
                ))
                .from(product)
                .leftJoin(product.brand, brand)
                .leftJoin(product.seller, seller)
                .leftJoin(product.productPrice, productPrice)
                .leftJoin(product.productImages, productImage)
                .where(product.status.eq(ProductStatus.ACTIVE))
                .orderBy(product.createdAt.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<ProductDto> findPopularProducts() {
        return queryFactory
                .select(Projections.constructor(ProductDto.class,
                        product.id,
                        product.name,
                        product.slug,
                        product.shortDescription,
                        productPrice.basePrice,
                        productPrice.salePrice,
                        productPrice.currency,
                        productImage.url,
                        productImage.altText,
                        brand.id,
                        brand.name,
                        seller.id,
                        seller.name,
                        // 서브쿼리들
                        queryFactory.select(review.rating.avg())
                                .from(review)
                                .where(review.product.id.eq(product.id)),
                        queryFactory.select(review.count())
                                .from(review)
                                .where(review.product.id.eq(product.id)),
                        queryFactory.select(option.stock.sum().gt(0))
                                .from(option)
                                .innerJoin(option.optionGroup, productOptionGroup)
                                .where(productOptionGroup.product.id.eq(product.id)),
                        product.status,
                        product.createdAt
                ))
                .from(product)
                .leftJoin(product.brand, brand)
                .leftJoin(product.seller, seller)
                .leftJoin(product.productPrice, productPrice)
                .leftJoin(product.productImages, productImage)
                .where(product.status.eq(ProductStatus.ACTIVE))
                .orderBy(product.createdAt.desc())
                .limit(10)
                .fetch();

    }

    @Override
    public List<CategoryDto> findFeaturedCategories() {
        return queryFactory
                .select(Projections.constructor(CategoryDto.class,
                        category.id,
                        category.name,
                        category.slug,
                        category.imageUrl,
                        // 서브쿼리로 카테고리 상품 수
                        queryFactory
                                .select(productCategory.product.count())
                                .from(productCategory)
                                .where(productCategory.category.id.eq(category.id))
                ))
                .from(category)
                .where(category.level.eq(1))
                .limit(5)
                .fetch();

    }
}
