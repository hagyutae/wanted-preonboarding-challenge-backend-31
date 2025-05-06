package com.challenge.onboarding.product.service.dto.request;

import java.util.List;

public record CreateProductRequest(
    String name,
    String slug,
    String shortDescription,
    String fullDescription,
    Long sellerId,
    Long brandId,
    String status,
    Detail detail,
    Price price,
    List<CategoryMapping> categories,
    List<OptionGroup> optionGroups,
    List<Image> images,
    List<Long> tags
) {
    public record Detail(
        double weight,
        Dimensions dimensions,
        String materials,
        String countryOfOrigin,
        String warrantyInfo,
        String careInstructions,
        AdditionalInfo additionalInfo
    ) {}

    public record AdditionalInfo(
        String assembly_required,
        String assembly_time
    ) {}

    public record Dimensions(
        double width,
        double height,
        double depth
    ) {}

    public record Price(
        long basePrice,
        long salePrice,
        long costPrice,
        String currency,
        int taxRate
    ) {}

    public record CategoryMapping(
        Long categoryId,
        boolean isPrimary
    ) {}

    public record OptionGroup(
        String name,
        int displayOrder,
        List<Option> options
    ) {
        public record Option(
            String name,
            long additionalPrice,
            String sku,
            int stock,
            int displayOrder
        ) {}
    }

    public record Image(
        String url,
        String altText,
        boolean isPrimary,
        int displayOrder,
        Long optionId
    ) {}
}
