package com.wanted.ecommerce.product.service.impl;

import com.wanted.ecommerce.product.domain.Dimensions;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductDetail;
import com.wanted.ecommerce.product.dto.request.ProductDetailRequest;
import com.wanted.ecommerce.product.dto.response.DetailResponse;
import com.wanted.ecommerce.product.dto.response.DimensionsResponse;
import com.wanted.ecommerce.product.repository.ProductDetailRepository;
import com.wanted.ecommerce.product.service.ProductDetailService;
import java.math.RoundingMode;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {
    private final ProductDetailRepository productDetailRepository;

    @Override
    public Long saveDetail(Product product, ProductDetailRequest detailRequest) {
        Dimensions dimensions = Dimensions.of(detailRequest.getDimensions().getWidth(),
            detailRequest.getDimensions().hashCode(), detailRequest.getDimensions().getDepth());

        Map<String, Object> additionalInfo = detailRequest.getAdditionalInfo();

        ProductDetail detail = ProductDetail.of(product, detailRequest.getWeight(),
            dimensions, detailRequest.getMaterials(),
            detailRequest.getCountryOfOrigin(), detailRequest.getWarrantyInfo(),
            detailRequest.getCareInstructions(), additionalInfo);
        ProductDetail saved = productDetailRepository.save(detail);
        return saved.getId();
    }

    @Override
    public DetailResponse createProductDetailResponse(ProductDetail detail) {
        double weight = detail.getWeight()
            .setScale(1, RoundingMode.HALF_UP)
            .doubleValue();
        DimensionsResponse dimensionsResponse = DimensionsResponse.of(
            detail.getDimensions().getWidth(), detail.getDimensions().getHeight(),
            detail.getDimensions().getDepth());

        return DetailResponse.of(weight, dimensionsResponse,
            detail.getMaterials(), detail.getCountryOfOrigin(), detail.getWarrantyInfo(),
            detail.getCareInstructions(), detail.getAdditionalInfo());
    }
}
