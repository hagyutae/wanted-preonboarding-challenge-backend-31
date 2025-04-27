package com.mkhwang.wantedcqrs.product.infra.impl;

import com.mkhwang.wantedcqrs.product.domain.*;
import com.mkhwang.wantedcqrs.product.domain.dto.*;
import com.mkhwang.wantedcqrs.product.infra.ProductSearchRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class ProductSearchRepositoryImpl implements ProductSearchRepository {
  private final JPAQueryFactory jpaQueryFactory;
  private final QProduct qProduct = QProduct.product;
  private final QBrand qBrand = QBrand.brand;
  private final QSeller qSeller = QSeller.seller;
  private final QProductImage qProductImage = QProductImage.productImage;
  private final QProductPrice qProductPrice = QProductPrice.productPrice;
  private final QProductCategory qProductCategory = QProductCategory.productCategory;
  private final QProductOptionGroup qProductOptionGroup = QProductOptionGroup.productOptionGroup;
  private final QProductOption qProductOption = QProductOption.productOption;
  private final QProductTag qProductTag = QProductTag.productTag;
  private final QTag qTag = QTag.tag;


  @Override
  public Page<ProductSearchResultDto> searchProducts(ProductSearchDto searchDto) {
    Pageable pageable = searchDto.toPageable();
    BooleanBuilder condition = this.generateCondition(searchDto);

    long total = Optional.ofNullable(
                    jpaQueryFactory.select(qProduct.count())
                            .from(qProduct)
                            .where(condition)
                            .fetchOne())
            .orElse(0L);

    if (total == 0) {
      return Page.empty();
    }

//    List<ProductSearchResultDto> result = null;

    List<ProductSearchResultDto> result = jpaQueryFactory
            .select(new QProductSearchResultDto(
                    qProduct.id,
                    qProduct.name,
                    qProduct.slug,
                    qProduct.shortDescription,
                    qProduct.fullDescription,
                    new QSellerDto(
                            qSeller.id,
                            qSeller.name,
                            qSeller.logoUrl,
                            qSeller.rating,
                            qSeller.contactEmail,
                            qSeller.contactPhone,
                            qSeller.createdAt
                    ),
                    new QBrandDto(
                            qBrand.id,
                            qBrand.name,
                            qBrand.slug,
                            qBrand.logoUrl,
                            qBrand.website
                    ),
                    qProduct.status,
                    qProduct.createdAt,
                    new QProductSearchImageDto(
                            qProductImage.url,
                            qProductImage.altText
                    )
            ))
            .from(qProduct)
            .join(qProduct.brand, qBrand)
            .join(qProduct.seller, qSeller)
            .leftJoin(qProductImage)
            .on(qProduct.id.eq(qProductImage.product.id)
                    .and(qProductImage.primary.eq(true)))
            .where(condition)
            .orderBy(getOrderSpecifiers(pageable, qProduct.getType(), qProduct.getMetadata().getName()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();


    return new PageImpl<>(result, pageable, total);
  }

  @Override
  public ProductSearchDetailDto getProductDetailBaseById(Long id) {
    return jpaQueryFactory
            .select(
                    Projections.constructor(
                            ProductSearchDetailDto.class,
                            qProduct.id,
                            qProduct.name,
                            qProduct.slug,
                            qProduct.shortDescription,
                            qProduct.fullDescription,
                            qProduct.status,
                            qProduct.createdAt,
                            qProduct.updatedAt,
                            Projections.constructor(
                                    SellerDto.class,
                                    qSeller.id,
                                    qSeller.name,
                                    qSeller.logoUrl,
                                    qSeller.rating,
                                    qSeller.contactEmail,
                                    qSeller.contactPhone
                            ),
                            Projections.constructor(
                                    BrandDto.class,
                                    qBrand.id,
                                    qBrand.name,
                                    qBrand.slug,
                                    qBrand.logoUrl,
                                    qBrand.website
                            ),
                            Projections.constructor(
                                    ProductPriceDto.class,
                                    qProductPrice.basePrice,
                                    qProductPrice.salePrice,
                                    qProductPrice.costPrice,
                                    qProductPrice.currency,
                                    qProductPrice.taxRate
                                    )
                    )
            )
            .from(qProduct)
            .join(qProduct.brand, qBrand)
            .join(qProduct.seller, qSeller)
            .join(qProductPrice).on(
                    qProductPrice.product.eq(qProduct)
            )
            .where(qProduct.id.eq(id))
            .fetchOne();
  }

  @Override
  public List<TagDto> findTagsByProductId(Long productId) {
    return jpaQueryFactory
            .select(Projections.constructor(
                    TagDto.class,
                    qTag.id,
                    qTag.name,
                    qTag.slug
            ))
            .from(qProductTag)
            .join(qProductTag.tag, qTag)
            .where(qProductTag.product.id.eq(productId))
            .fetch();
  }

  private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable, Class<?> entityClass, String alias) {
    PathBuilder<?> pathBuilder = new PathBuilder<>(entityClass, alias);

    return pageable.getSort().stream()
            .map(order -> {
              Order direction = order.isAscending() ? Order.ASC : Order.DESC;
              ComparableExpression<?> expression = pathBuilder.getComparable(order.getProperty(), Comparable.class);
              return new OrderSpecifier<>(direction, expression);
            })
            .toArray(OrderSpecifier<?>[]::new);
  }

  private BooleanBuilder generateCondition(ProductSearchDto searchDto) {
    BooleanBuilder condition = new BooleanBuilder();

    if (StringUtils.hasText(searchDto.getSearch())) {
      condition.and(qProduct.name.containsIgnoreCase(searchDto.getSearch())
              .or(qProduct.shortDescription.containsIgnoreCase(searchDto.getSearch())
                      .or(qProduct.fullDescription.containsIgnoreCase(searchDto.getSearch()))
                      .or(qProduct.slug.containsIgnoreCase(searchDto.getSearch()))
              ));
    }
    if (searchDto.getStatus() != null) {
      condition.and(qProduct.status.eq(searchDto.getStatus()));
    }
    if (searchDto.getMinPrice() != null) {

      condition.and(
              JPAExpressions.selectOne()
                      .from(qProductPrice)
                      .where(qProductPrice.product.eq(qProduct)
                              .and(qProductPrice.salePrice.goe(searchDto.getMinPrice())))
                      .exists()
      );
    }
    if (searchDto.getMaxPrice() != null) {
      condition.and(
              JPAExpressions.selectOne()
                      .from(qProductPrice)
                      .where(qProductPrice.product.eq(qProduct)
                              .and(qProductPrice.salePrice.loe(searchDto.getMaxPrice())))
                      .exists()
      );
    }
    if (searchDto.getCategory() != null && !searchDto.getCategory().isEmpty()) {
      condition.and(
              JPAExpressions.selectOne()
                      .from(qProductCategory)
                      .where(qProductCategory.product.eq(qProduct)
                              .and(qProductCategory.category.id.in(searchDto.getCategory())))
                      .exists()
      );
    }
    if (searchDto.getSeller() != null) {
      condition.and(qProduct.seller.id.eq(searchDto.getSeller()));
    }
    if (searchDto.getBrand() != null) {
      condition.and(qProduct.brand.id.eq(searchDto.getBrand()));
    }
    if (searchDto.getInStock() != null) {
      condition.and(
              JPAExpressions.selectOne()
                      .from(qProductOptionGroup)
                      .join(qProductOption)
                      .on(qProductOption.group.eq(qProductOptionGroup))
                      .where(qProductOptionGroup.product.eq(qProduct)
                              .and(searchDto.getInStock() ?
                                      qProductOption.stock.gt(1)
                                      : qProductOption.stock.eq(0))
                      ).exists()
      );
    }

    return condition;
  }
}
