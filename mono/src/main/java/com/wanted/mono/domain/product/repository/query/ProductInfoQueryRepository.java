package com.wanted.mono.domain.product.repository.query;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.mono.domain.category.entity.Category;
import com.wanted.mono.domain.category.entity.ProductCategory;
import com.wanted.mono.domain.category.entity.dto.ProductCategoryDto;
import com.wanted.mono.domain.category.repository.CategoryRepository;
import com.wanted.mono.domain.category.repository.query.CategoryQueryRepository;
import com.wanted.mono.domain.product.dto.Dimension;
import com.wanted.mono.domain.product.dto.RatingDto;
import com.wanted.mono.domain.product.dto.model.*;
import com.wanted.mono.domain.product.entity.ProductImage;
import com.wanted.mono.domain.product.entity.ProductOption;
import com.wanted.mono.domain.product.entity.ProductOptionGroup;
import com.wanted.mono.domain.tag.entity.ProductTag;
import com.wanted.mono.domain.tag.entity.Tag;
import com.wanted.mono.domain.tag.entity.dto.TagDto;
import com.wanted.mono.domain.tag.repository.TagRepository;
import com.wanted.mono.domain.tag.repository.query.ProductTagQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.wanted.mono.domain.brand.entity.QBrand.brand;
import static com.wanted.mono.domain.product.entity.QProduct.product;
import static com.wanted.mono.domain.product.entity.QProductDetail.productDetail;
import static com.wanted.mono.domain.product.entity.QProductPrice.productPrice;
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


        //dto.setRelatedProducts(productRepository.findRelatedByProductId(productId));
        //dto.setRating(ratingService.calculate(productId)); // 계산형이면 직접 처리

        return dto;
    }


}
