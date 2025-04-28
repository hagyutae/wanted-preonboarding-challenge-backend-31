package com.mkhwang.wantedcqrs.product.infra.impl;

import com.mkhwang.wantedcqrs.common.querydsl.QuerydslUtil;
import com.mkhwang.wantedcqrs.config.advice.dto.ApiPageInfo;
import com.mkhwang.wantedcqrs.product.domain.*;
import com.mkhwang.wantedcqrs.product.domain.dto.QBrandDto;
import com.mkhwang.wantedcqrs.product.domain.dto.QSellerDto;
import com.mkhwang.wantedcqrs.product.domain.dto.category.*;
import com.mkhwang.wantedcqrs.product.infra.CategoryRepository;
import com.mkhwang.wantedcqrs.product.infra.CategorySearchRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;


@Component
@RequiredArgsConstructor
public class CategorySearchRepositoryImpl implements CategorySearchRepository {
  private final JPAQueryFactory jpaQueryFactory;
  private final CategoryRepository categoryRepository;
  private final QuerydslUtil querydslUtil;
  private final QProduct qProduct = QProduct.product;
  private final QBrand qBrand = QBrand.brand;
  private final QSeller qSeller = QSeller.seller;
  private final QProductImage qProductImage = QProductImage.productImage;
  private final QProductPrice qProductPrice = QProductPrice.productPrice;
  private final QProductCategory qProductCategory = QProductCategory.productCategory;
  private final QProductOptionGroup qProductOptionGroup = QProductOptionGroup.productOptionGroup;
  private final QProductOption qProductOption = QProductOption.productOption;
  private final QReview qReview = QReview.review;

  @Override
  public CategorySearchResultDto searchCategories(CategorySearchDto searchDto) {
    Category existCategory = categoryRepository.findById(searchDto.getCategoryId()).orElseThrow(
            () -> new IllegalArgumentException("Category not found"));
    CategorySearchCategoryDto category = CategorySearchCategoryDto.of(
            existCategory.getId(),
            existCategory.getName(),
            existCategory.getSlug(),
            existCategory.getDescription(),
            existCategory.getLevel(),
            existCategory.getImageUrl(),
            existCategory.getParent() != null ? CategoryParentDto.of(
                    existCategory.getParent().getId(),
                    existCategory.getParent().getName(),
                    existCategory.getParent().getSlug()
            ) : null
    );

    Pageable pageable = searchDto.toPageable();

    BooleanBuilder condition = buildSearchConditions(searchDto);

    long total = Optional.ofNullable(
            jpaQueryFactory.select(qProduct.count())
                    .from(qProduct).where(condition).fetchOne()

    ).orElse(0L);


    if (total == 0) {
      return CategorySearchResultDto.of(
              category,
              List.of(),
              ApiPageInfo.empty(pageable)
      );
    }

    List<CategoryProduct> items = jpaQueryFactory
            .select(new QCategoryProduct(
                    qProduct.id,
                    qProduct.name,
                    qProduct.slug,
                    qProduct.shortDescription,
                    qProductPrice.basePrice,
                    qProductPrice.salePrice,
                    qProductPrice.currency,
                    new QCategoryProductImageDto(
                            qProductImage.url,
                            qProductImage.altText
                    ),
                    new QBrandDto(
                            qBrand.id,
                            qBrand.name,
                            qBrand.slug,
                            qBrand.logoUrl,
                            qBrand.website
                    ),
                    new QSellerDto(
                            qSeller.id,
                            qSeller.name,
                            qSeller.logoUrl,
                            qSeller.rating,
                            qSeller.contactEmail,
                            qSeller.contactPhone,
                            qSeller.createdAt
                    ),
                    qProduct.status,
                    qProduct.createdAt

            ))
            .from(qProduct)
            .join(qProduct.brand, qBrand)
            .join(qProduct.seller, qSeller)
            .join(qProductPrice).on(
                    qProductPrice.product.eq(qProduct)
            )
            .leftJoin(qProductImage)
            .on(qProduct.id.eq(qProductImage.product.id)
                    .and(qProductImage.primary.eq(true)))
            .where(condition)
            .orderBy(querydslUtil.getOrderSpecifiers(pageable, qProduct.getType(), qProduct.getMetadata().getName()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();


    List<Long> productIds = items.stream().mapToLong(CategoryProduct::getId).boxed().toList();

    List<Long> inStockProductIds = jpaQueryFactory.select(qProductOptionGroup.product.id)
            .from(qProductOptionGroup)
            .where(qProductOptionGroup.product.id.in(
                    productIds
            ).and(
                    JPAExpressions.selectOne().from(qProductOption).where(
                            qProductOption.group.eq(qProductOptionGroup).and(
                                    qProductOption.stock.gt(0)
                            )
                    ).exists()
            ))
            .fetch();

    List<Tuple> rating = jpaQueryFactory.select(
                    qReview.product.id,
                    qReview.rating.avg(),
                    qReview.rating.count()
            ).from(qReview).where(qReview.product.id.in(productIds))
            .groupBy(qReview.product.id).fetch();

    items.forEach(item -> {
      for (Tuple tuple : rating) {
        item.setInStock(inStockProductIds.contains(item.getId()));
        if (Objects.equals(item.getId(), tuple.get(qReview.product.id))) {
          item.setRating(tuple.get(qReview.rating.avg()));
          item.setReviewCount(tuple.get(qReview.rating.count()));
        }
      }
    });


    return CategorySearchResultDto.of(
            category,
            items,
            ApiPageInfo.of(
                    total,
                    (long) Math.ceil((double) total / pageable.getPageSize()),
                    pageable.getPageNumber() + 1,
                    pageable.getPageSize()
            )
    );
  }

  private BooleanBuilder buildSearchConditions(CategorySearchDto searchDto) {
    BooleanBuilder builder = new BooleanBuilder();

    List<Long> categoryIds = new ArrayList<>();
    categoryIds.add(searchDto.getCategoryId());

    if (Boolean.TRUE.equals(searchDto.getIncludeSubcategories())) {
      categoryIds.addAll(this.getAllSubcategories(searchDto.getCategoryId()));
    }

    builder.and(
            JPAExpressions.selectOne().from(qProductCategory)
                    .where(qProductCategory.category.id.in(categoryIds)
                            .and(qProductCategory.product.eq(qProduct)))
                    .exists()
    );
    return builder;
  }

  private List<Long> getAllSubcategories(Long categoryId) {
    List<Category> subCategories = categoryRepository.findAll();
    return this.recursiveGetSubcategories(categoryId, subCategories);
  }

  private List<Long> recursiveGetSubcategories(Long categoryId, List<Category> allCategories) {
    List<Long> directChildren = allCategories.stream()
            .filter(category -> category.getParent() != null
                    && Objects.equals(category.getParent().getId(), categoryId))
            .map(Category::getId)
            .toList();

    return Stream.concat(
                    directChildren.stream(),
                    directChildren.stream()
                            .flatMap(childId -> recursiveGetSubcategories(childId, allCategories).stream())
            )
            .toList();
  }
}
