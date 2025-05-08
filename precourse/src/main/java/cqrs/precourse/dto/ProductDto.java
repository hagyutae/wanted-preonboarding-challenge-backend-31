package cqrs.precourse.dto;

import cqrs.precourse.domain.Product;

import java.time.LocalDateTime;
import java.util.List;

public class ProductDto {
    public record ProductCreateRequestDto(
            String name,
            String slug,
            String shortDescription,
            String fullDescription,
            Long sellerId,
            Long brandId,
            String status,
            ProductDetailDto detail,
            PriceDto price,
            List<CategoryDto> categories,
            List<OptionGroupDto> optionGroup,
            List<ImagesDto> images,
            List<Integer> tag
    ) {}

    public record ProductDetailDto(
            Float weight,
            DimensionsDto dimensions,
            String materials,
            String countryOfOrigin,
            String warrantyInfo,
            String careInstructions,
            AdditionalInfoDto additionalInfo
    ) {}

    public record DimensionsDto(
            int width,
            int height,
            int depth
    ) {}

    public record AdditionalInfoDto(
            Boolean assemblyRequired,
            String assemblyTime
    ) {}

    public record PriceDto(
            int basePrice,
            int salePrice,
            int costPrice,
            String currency,
            int taxRate
    ) {}

    public record CategoryDto(
            Long categoryId,
            Boolean isPrimary
    ) {}

    public record OptionGroupDto(
            String name,
            int displayOrder,
            List<OptionDto> options
    ) {}

    public record OptionDto(
            String name,
            int additionalPrice,
            String sku,
            int stock,
            int displayOrder
    ) {}

    public record ImagesDto(
            String url,
            String altText,
            Boolean isPrimary,
            Long displayOrder,
            Long optionId

    ) {}

    public record ProductCreateResponseDto(
          Long id,
          String name,
          String slug,
          LocalDateTime createdAt,
          LocalDateTime updatedAt
    ) {
        public static ProductCreateResponseDto of(Product product) {
            return new ProductCreateResponseDto(
                    product.getId(),
                    product.getName(),
                    product.getSlug(),
                    product.getCreatedAt(),
                    product.getUpdatedAt()
            );
        }
    }

}
