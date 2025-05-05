package com.preonboarding.challenge.service.query;

import com.preonboarding.challenge.service.product.ProductDto;
import com.preonboarding.challenge.service.query.entity.ProductDocument;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * MongoDB 상품 문서를 DTO로 변환하는 매퍼
 */
@Component
public class ProductDocumentMapper {

    /**
     * ProductDocument를 ProductDto.Product로 변환
     */
    public ProductDto.Product toProductDto(ProductDocument document) {
        if (document == null) {
            return null;
        }

        ProductDto.Product.ProductBuilder builder = ProductDto.Product.builder()
                .id(document.getId())
                .name(document.getName())
                .slug(document.getSlug())
                .shortDescription(document.getShortDescription())
                .fullDescription(document.getFullDescription())
                .status(document.getStatus())
                .createdAt(document.getCreatedAt())
                .updatedAt(document.getUpdatedAt());

        // 판매자 정보 설정
        if (document.getSeller() != null) {
            builder.seller(ProductDto.Seller.builder()
                    .id(document.getSeller().getId())
                    .name(document.getSeller().getName())
                    .build());
        }

        // 브랜드 정보 설정
        if (document.getBrand() != null) {
            builder.brand(ProductDto.Brand.builder()
                    .id(document.getBrand().getId())
                    .name(document.getBrand().getName())
                    .build());
        }

        // 상세 정보 설정
        if (document.getDetail() != null) {
            builder.detail(ProductDto.Detail.builder()
                    .weight(document.getDetail().getWeight())
                    .dimensions(document.getDetail().getDimensions())
                    .materials(document.getDetail().getMaterials())
                    .countryOfOrigin(document.getDetail().getCountryOfOrigin())
                    .warrantyInfo(document.getDetail().getWarrantyInfo())
                    .careInstructions(document.getDetail().getCareInstructions())
                    .additionalInfo(document.getDetail().getAdditionalInfo())
                    .build());
        }

        // 가격 정보 설정
        if (document.getPrice() != null) {
            builder.price(ProductDto.Price.builder()
                    .basePrice(document.getPrice().getBasePrice())
                    .salePrice(document.getPrice().getSalePrice())
                    .costPrice(document.getPrice().getCostPrice())
                    .currency(document.getPrice().getCurrency())
                    .taxRate(document.getPrice().getTaxRate())
                    .discountPercentage(document.getPrice().getDiscountPercentage())
                    .build());
        }

        // 카테고리 정보 설정
        if (document.getCategories() != null) {
            List<ProductDto.Category> categories = document.getCategories().stream()
                    .map(this::toCategoryDto)
                    .collect(Collectors.toList());
            builder.categories(categories);
        }

        // 옵션 그룹 정보 설정
        if (document.getOptionGroups() != null) {
            List<ProductDto.OptionGroup> optionGroups = document.getOptionGroups().stream()
                    .map(this::toOptionGroupDto)
                    .collect(Collectors.toList());
            builder.optionGroups(optionGroups);
        }

        // 이미지 정보 설정
        if (document.getImages() != null) {
            List<ProductDto.Image> images = document.getImages().stream()
                    .map(this::toImageDto)
                    .collect(Collectors.toList());
            builder.images(images);
        }

        // 태그 정보 설정
        if (document.getTags() != null) {
            List<ProductDto.Tag> tags = document.getTags().stream()
                    .map(this::toTagDto)
                    .collect(Collectors.toList());
            builder.tags(tags);
        }

        // 평점 정보 설정
        if (document.getRating() != null) {
            builder.rating(ProductDto.RatingSummary.builder()
                    .average(document.getRating().getAverage())
                    .count(document.getRating().getCount())
                    .distribution(document.getRating().getDistribution())
                    .build());
        }

        return builder.build();
    }

    /**
     * ProductDocument를 ProductDto.ProductSummary로 변환
     */
    public ProductDto.ProductSummary toProductSummaryDto(ProductDocument document) {
        if (document == null) {
            return null;
        }

        ProductDto.ProductSummary.ProductSummaryBuilder builder = ProductDto.ProductSummary.builder()
                .id(document.getId())
                .name(document.getName())
                .slug(document.getSlug())
                .shortDescription(document.getShortDescription())
                .createdAt(document.getCreatedAt())
                .status(document.getStatus())
                .inStock("ACTIVE".equals(document.getStatus()));

        // 판매자 정보 설정
        if (document.getSeller() != null) {
            builder.seller(ProductDto.Seller.builder()
                    .id(document.getSeller().getId())
                    .name(document.getSeller().getName())
                    .build());
        }

        // 브랜드 정보 설정
        if (document.getBrand() != null) {
            builder.brand(ProductDto.Brand.builder()
                    .id(document.getBrand().getId())
                    .name(document.getBrand().getName())
                    .build());
        }

        // 가격 정보 설정
        if (document.getPrice() != null) {
            builder.basePrice(document.getPrice().getBasePrice());
            builder.salePrice(document.getPrice().getSalePrice());
            builder.currency(document.getPrice().getCurrency());
        }

        // 리뷰 정보 설정
        if (document.getRating() != null) {
            builder.rating(document.getRating().getAverage());
            builder.reviewCount(document.getRating().getCount());
        }

        // 대표 이미지 설정
        if (document.getImages() != null && !document.getImages().isEmpty()) {
            Optional<ProductDocument.Image> primaryImage = document.getImages().stream()
                    .filter(ProductDocument.Image::isPrimary)
                    .findFirst();

            if (primaryImage.isPresent()) {
                builder.primaryImage(toImageDto(primaryImage.get()));
            }
        }

        return builder.build();
    }

    /**
     * 카테고리 정보 변환
     */
    private ProductDto.Category toCategoryDto(ProductDocument.CategoryInfo categoryInfo) {
        ProductDto.Category.CategoryBuilder builder = ProductDto.Category.builder()
                .id(categoryInfo.getId())
                .name(categoryInfo.getName())
                .slug(categoryInfo.getSlug());

        if (categoryInfo.getParent() != null) {
            builder.parent(ProductDto.ParentCategory.builder()
                    .id(categoryInfo.getParent().getId())
                    .name(categoryInfo.getParent().getName())
                    .slug(categoryInfo.getParent().getSlug())
                    .build());
        }

        return builder.build();
    }

    /**
     * 옵션 그룹 정보 변환
     */
    private ProductDto.OptionGroup toOptionGroupDto(ProductDocument.OptionGroup optionGroup) {
        List<ProductDto.Option> options = new ArrayList<>();
        if (optionGroup.getOptions() != null) {
            options = optionGroup.getOptions().stream()
                    .map(this::toOptionDto)
                    .collect(Collectors.toList());
        }

        return ProductDto.OptionGroup.builder()
                .id(optionGroup.getId())
                .name(optionGroup.getName())
                .displayOrder(optionGroup.getDisplayOrder())
                .options(options)
                .build();
    }

    /**
     * 옵션 정보 변환
     */
    private ProductDto.Option toOptionDto(ProductDocument.Option option) {
        return ProductDto.Option.builder()
                .id(option.getId())
                .optionGroupId(option.getId()) // 옵션 그룹 ID는 별도로 설정해야 함
                .name(option.getName())
                .additionalPrice(option.getAdditionalPrice())
                .sku(option.getSku())
                .stock(option.getStock())
                .displayOrder(option.getDisplayOrder())
                .build();
    }

    /**
     * 이미지 정보 변환
     */
    private ProductDto.Image toImageDto(ProductDocument.Image image) {
        return ProductDto.Image.builder()
                .id(image.getId())
                .url(image.getUrl())
                .altText(image.getAltText())
                .isPrimary(image.isPrimary())
                .displayOrder(image.getDisplayOrder())
                .optionId(image.getOptionId())
                .build();
    }

    /**
     * 태그 정보 변환
     */
    private ProductDto.Tag toTagDto(ProductDocument.TagInfo tagInfo) {
        return ProductDto.Tag.builder()
                .id(tagInfo.getId())
                .name(tagInfo.getName())
                .slug(tagInfo.getSlug())
                .build();
    }
}