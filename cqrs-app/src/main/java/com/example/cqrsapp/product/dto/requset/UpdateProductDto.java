package com.example.cqrsapp.product.dto.requset;

import com.example.cqrsapp.product.domain.ProductStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateProductDto {
    private String name;
    private String slug;
    private String shortDescription;
    private String fullDescription;
    private Long sellerId;
    private Long brandId;
    private ProductStatus status;
    private RegisterProductDto.DetailDto detail;
    private RegisterProductDto.PriceDto price;
    private List<RegisterProductDto.CategoryDto> categories;
    private List<RegisterProductDto.OptionGroupDto> optionGroups;
    private List<RegisterProductDto.ImageDto> images;
    private List<Long> tags;

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DetailDto {
        private Double weight;
        private RegisterProductDto.DimensionsDto dimensions;
        private String materials;
        private String countryOfOrigin;
        private String warrantyInfo;
        private String careInstructions;
        private Map<String, String> additionalInfo;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DimensionsDto {
        private Integer width;
        private Integer height;
        private Integer depth;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class AdditionalInfoDto {
        private Boolean assemblyRequired;
        private String assemblyTime;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class PriceDto {
        private Integer basePrice;
        private Integer salePrice;
        private Integer costPrice;
        private String currency;
        private Integer taxRate;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CategoryDto {
        private Long categoryId;
        private Boolean isPrimary;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class OptionGroupDto {
        private String name;
        private Integer displayOrder;
        private List<RegisterProductDto.OptionDto> options;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class OptionDto {
        private String name;
        private Integer additionalPrice;
        private String sku;
        private Integer stock;
        private Integer displayOrder;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ImageDto {
        private String url;
        private String altText;
        private Boolean isPrimary;
        private Integer displayOrder;
        private Long optionId;
    }
}
