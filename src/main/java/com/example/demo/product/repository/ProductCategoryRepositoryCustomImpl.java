package com.example.demo.product.repository;

import com.example.demo.category.entity.QCategoryEntity;
import com.example.demo.product.entity.ProductCategoryEntity;
import com.example.demo.product.dto.FeaturedCategory;
import com.example.demo.product.dto.QFeaturedCategory;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.example.demo.brand.entity.QBrandEntity.brandEntity;
import static com.example.demo.category.entity.QCategoryEntity.categoryEntity;
import static com.example.demo.product.entity.QProductCategoryEntity.productCategoryEntity;
import static com.example.demo.product.entity.QProductDetailEntity.productDetailEntity;
import static com.example.demo.product.entity.QProductEntity.productEntity;
import static com.example.demo.product.entity.QProductPriceEntity.productPriceEntity;
import static com.example.demo.user.entity.QSellerEntity.sellerEntity;
;

@Repository
@RequiredArgsConstructor
public class ProductCategoryRepositoryCustomImpl implements ProductCategoryRepositoryCustom {
    private final JPAQueryFactory qf;

    public List<ProductCategoryEntity> findAllByProductIds(Collection<Long> productIds) {
        return qf.select(productCategoryEntity)
                .from(productCategoryEntity)
                .where(productCategoryEntity.productEntity.id.in(productIds))
                .fetch();
    }

    @Override
    public List<ProductCategoryEntity> findAllByProductId(Long productId) {
        QCategoryEntity parentCategory = new QCategoryEntity("parentCategory");

        return qf.select(productCategoryEntity)
                .from(productCategoryEntity)
                .leftJoin(productCategoryEntity.categoryEntity, categoryEntity).fetchJoin()
                .leftJoin(categoryEntity.parent, parentCategory).fetchJoin()
                .where(productCategoryEntity.productEntity.id.eq(productId))
                .fetch();
    }

    @Override
    public Long deleteByProductId(Long productId) {
        return qf.delete(productCategoryEntity)
                .where(productCategoryEntity.productEntity.id.eq(productId))
                .execute();
    }

    @Override
    public List<FeaturedCategory> findFeaturedCategories() {
        NumberExpression<Long> productCount = productCategoryEntity.productEntity.count();

        return qf.select(new QFeaturedCategory(
                        productCategoryEntity.categoryEntity.id,
                        productCategoryEntity.categoryEntity.name,
                        productCategoryEntity.categoryEntity.slug,
                        productCategoryEntity.categoryEntity.imageUrl,
                        productCount
                ))
                .from(productCategoryEntity)
                .leftJoin(productCategoryEntity.categoryEntity, categoryEntity)
                .groupBy(
                        productCategoryEntity.categoryEntity.id,
                        productCategoryEntity.categoryEntity.name,
                        productCategoryEntity.categoryEntity.slug,
                        productCategoryEntity.categoryEntity.imageUrl
                )
                .orderBy(productCount.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<ProductCategoryEntity> findAllByCategoryIds(List<Long> categoryIdList) {
        return qf.selectFrom(productCategoryEntity)
                .leftJoin(productCategoryEntity.productEntity, productEntity).fetchJoin()
                .leftJoin(productEntity.productDetailEntity, productDetailEntity).fetchJoin()
                .leftJoin(productEntity.productPriceEntity, productPriceEntity).fetchJoin()
                .leftJoin(productEntity.sellerEntity, sellerEntity).fetchJoin()
                .leftJoin(productEntity.brandEntity, brandEntity).fetchJoin()
                .where(productCategoryEntity.categoryEntity.id.in(categoryIdList))
                .fetch();
    }
}
