package com.wanted.mono.domain.product.repository.query;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.mono.domain.category.entity.Category;
import com.wanted.mono.domain.category.entity.ProductCategory;
import com.wanted.mono.domain.category.entity.dto.ProductCategoryDto;
import com.wanted.mono.domain.category.repository.CategoryRepository;
import com.wanted.mono.domain.category.repository.query.CategoryQueryRepository;
import com.wanted.mono.domain.product.dto.Dimension;
import com.wanted.mono.domain.product.dto.ProductImageSearchDto;
import com.wanted.mono.domain.product.dto.ProductRelatedDto;
import com.wanted.mono.domain.product.dto.RatingDto;
import com.wanted.mono.domain.product.dto.model.*;
import com.wanted.mono.domain.product.entity.*;
import com.wanted.mono.domain.product.repository.ProductRepository;
import com.wanted.mono.domain.tag.entity.ProductTag;
import com.wanted.mono.domain.tag.entity.Tag;
import com.wanted.mono.domain.tag.entity.dto.TagDto;
import com.wanted.mono.domain.tag.repository.TagRepository;
import com.wanted.mono.domain.tag.repository.query.ProductTagQueryRepository;
import com.wanted.mono.global.exception.ProductEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.wanted.mono.domain.brand.entity.QBrand.brand;
import static com.wanted.mono.domain.product.entity.QProduct.product;
import static com.wanted.mono.domain.product.entity.QProductDetail.productDetail;
import static com.wanted.mono.domain.product.entity.QProductImage.productImage;
import static com.wanted.mono.domain.product.entity.QProductPrice.productPrice;
import static com.wanted.mono.domain.review.entity.QReview.review;
import static com.wanted.mono.domain.seller.entity.QSeller.seller;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ProductInfoQueryRepository {
    private final JPAQueryFactory queryFactory;
    private final CategoryQueryRepository categoryQueryRepository;
    private final ProductOptionGroupQueryRepository productOptionGroupQueryRepository;
    private final ProductImageQueryRepository productImageQueryRepository;
    private final ProductTagQueryRepository productTagQueryRepository;
    private final ProductRepository productRepository;

    public ProductInfoDto getProductInfo(Long productId) {
        log.info("ProductInfoQueryRepository 단일 조인 DTO Projection");
        ProductInfoDto dto = queryFactory.select(
                        Projections.constructor(ProductInfoDto.class,
                                product.id,
                                product.name,
                                product.slug,
                                product.shortDescription,
                                product.fullDescription,
                                Projections.constructor(SellerDto.class,
                                        seller.id,
                                        seller.name,
                                        seller.description,
                                        seller.logoUrl,
                                        seller.rating,
                                        seller.contactEmail,
                                        seller.contactPhone
                                ),
                                Projections.constructor(BrandDto.class,
                                        brand.id,
                                        brand.name,
                                        brand.description,
                                        brand.logoUrl,
                                        brand.website
                                ),
                                product.status,
                                product.createdAt,
                                product.updatedAt,
                                Projections.constructor(ProductDetailDto.class,
                                        productDetail.weight,
                                        productDetail.dimensions,
                                        productDetail.materials,
                                        productDetail.countryOfOrigin,
                                        productDetail.warrantyInfo,
                                        productDetail.careInstructions,
                                        productDetail.additionalInfo
                                ),
                                Projections.constructor(ProductPriceDto.class,
                                        productPrice.basePrice,
                                        productPrice.salePrice,
                                        productPrice.currency,
                                        productPrice.taxRate
                                )
                        )
                )
                .from(product)
                .leftJoin(product.brand, brand)
                .leftJoin(product.seller, seller)
                .leftJoin(product.productDetails, productDetail)
                .leftJoin(product.productPrices, productPrice)
                .where(product.id.eq(productId))
                .fetchOne();

        log.error("조회 실패시 (없는 상품일시) 예외 처리");
        if (dto == null) {
            throw new ProductEmptyException();
        }

        log.info("ProductPriceDto 할인률 계산");
        dto.getPrice().addDiscountPercentage();

        log.info("==========복수 조인 개별 조회==========");

        log.info("ProductCategory to ProductCategory DTO");
        List<ProductCategory> productCategories = categoryQueryRepository.findProductCategories(productId);
        List<ProductCategoryDto> productCategoryDtos = productCategories.stream()
                .map(ProductCategoryDto::of)
                .toList();
        dto.setCategories(productCategoryDtos);

        log.info("ProductOptionGroup to ProductOptionGroup DTO");
        List<ProductOptionGroup> productOptionGroups = productOptionGroupQueryRepository.findProductOptionGroup(productId);
        List<ProductOptionGroupDto> productOptionGroupDtos = productOptionGroups.stream()
                .map(productOptionGroup -> {
                    List<ProductOption> productOptions = productOptionGroup.getProductOptions();
                    List<ProductOptionDto> productOptionDtos = productOptions.stream().map(ProductOptionDto::of).toList();

                    return ProductOptionGroupDto.of(productOptionGroup, productOptionDtos);
                })
                .toList();
        dto.setOptionGroups(productOptionGroupDtos);

        log.info("ProductImage to ProductImage DTO");
        List<ProductImage> productImages = productImageQueryRepository.findByProductIdWithProductOption(productId);
        List<ProductImageDto> productImageDtos = productImages.stream()
                .map(ProductImageDto::of)
                .toList();
        dto.setImages(productImageDtos);

        log.info("Tag to Tag DTO");
        List<ProductTag> productTags = productTagQueryRepository.findProductTagsByProductIdWithTag(productId);
        List<TagDto> tagDtos = productTags.stream()
                .map(productTag -> {
                    Tag tag = productTag.getTag();
                    return TagDto.of(tag);
                })
                .toList();
        dto.setTags(tagDtos);

        log.info("Related Product to Related Product DTO");
        List<Product> relatedProducts = getRelatedProduct(productId);
        List<ProductRelatedDto> relatedDtos = relatedProducts.stream()
                .map(relatedProduct -> {

                    ProductPrice productPrice = relatedProduct.getProductPrices().get(0);
                    ProductImage productImage = relatedProduct.getProductImages().get(0);

                    return ProductRelatedDto.of(relatedProduct,
                            productImage.getUrl(),
                            productImage.getAltText(),
                            productPrice.getBasePrice(),
                            productPrice.getSalePrice(),
                            productPrice.getCurrency()
                    );
                })
                .toList();
        dto.setRelatedProducts(relatedDtos);

        RatingDto ratingDto = getRatingDto(productId);
        dto.setRating(ratingDto);

        return dto;
    }

    public RatingDto getRatingDto(Long productId) {
        List<Tuple> result = queryFactory
                .select(review.rating, review.rating.count())
                .from(review)
                .where(review.product.id.eq(productId))
                .groupBy(review.rating)
                .fetch();

        Double average = queryFactory
                .select(review.rating.avg())
                .from(review)
                .where(review.product.id.eq(productId))
                .fetchOne();

        Map<String, Integer> distribution = new HashMap<>();
        long total = 0L;
        for (Tuple tuple : result) {
            String star = tuple.get(review.rating).toString();
            Long cnt = tuple.get(review.rating.count());
            distribution.put(star, cnt.intValue());
            total += cnt;
        }

        return new RatingDto(average, total, distribution);
    }

    public List<Product> getRelatedProduct(Long productId) {
        // 일단 브랜드가 같은 상품 추천
        return queryFactory.select(product)
                .from(product)
                .join(product.productImages, productImage).fetchJoin()
                .join(product.productPrices, productPrice)
                .where(
                        product.brand.id.in(
                                JPAExpressions.select(product.brand.id)
                                        .from(product)
                                        .where(product.id.eq(productId))
                        ),
                        product.id.ne(productId) // 자기 자신 제외
                )
                .limit(5)
                .fetch();
    }

}
