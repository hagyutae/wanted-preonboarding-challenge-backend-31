package kr.co.wanted.backend31.controller.vo;

import org.hibernate.validator.constraints.URL;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import kr.co.wanted.backend31.common.model.product.specification.ProductImageCreateSpecification;

public record ProductImageCreateRequest(@URL String url, @NotBlank String altText, boolean isPrimary,
        @Positive int displayOrder, @Nullable Long optionId) {

    public ProductImageCreateSpecification to() {
        return ProductImageCreateSpecification.builder()
                .url(url)
                .altText(altText)
                .isPrimary(isPrimary)
                .displayOrder(displayOrder)
                .optionId(optionId)
                .build();
    }

}
