package com.ecommerce.product.domain;

import com.ecommerce.product.application.dto.req.ProductCreateRequest;

public record AdditionalInfoVO(Boolean assemblyRequired, String assemblyTime) {
    public static AdditionalInfoVO toAdditionalInfoVO(ProductCreateRequest.ProductDetailRequest.AdditionalInfoRequest additionalInfoRequest) {
        return new AdditionalInfoVO(additionalInfoRequest.assemblyRequired(), additionalInfoRequest.assemblyTime());
    }
}
