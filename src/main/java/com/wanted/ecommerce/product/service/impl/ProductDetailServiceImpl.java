package com.wanted.ecommerce.product.service.impl;

import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.exception.ResourceNotFoundException;
import com.wanted.ecommerce.product.domain.Dimensions;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductDetail;
import com.wanted.ecommerce.product.dto.request.ProductDetailRequest;
import com.wanted.ecommerce.product.dto.request.ProductDetailRequest.DimensionsRequest;
import com.wanted.ecommerce.product.dto.response.ProductDetailResponse.DetailResponse;
import com.wanted.ecommerce.product.dto.response.ProductDetailResponse.DimensionsResponse;
import com.wanted.ecommerce.product.repository.ProductDetailRepository;
import com.wanted.ecommerce.product.service.ProductDetailService;
import java.math.RoundingMode;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;

    @Transactional
    @Override
    public ProductDetail saveDetail(Product product, ProductDetailRequest detailRequest) {
        Dimensions dimensions = Dimensions.of(detailRequest.getDimensions().getWidth(),
            detailRequest.getDimensions().hashCode(), detailRequest.getDimensions().getDepth());

        Map<String, Object> additionalInfo = detailRequest.getAdditionalInfo();

        ProductDetail detail = ProductDetail.of(product, detailRequest.getWeight(),
            dimensions, detailRequest.getMaterials(),
            detailRequest.getCountryOfOrigin(), detailRequest.getWarrantyInfo(),
            detailRequest.getCareInstructions(), additionalInfo);
        return productDetailRepository.save(detail);
    }

    @Transactional
    @Override
    public void updateDetail(ProductDetail detail, ProductDetailRequest request) {
        DimensionsRequest dimensionsRequest = request.getDimensions();
        Dimensions dimensions = Dimensions.of(dimensionsRequest.getWidth(),
            dimensionsRequest.getHeight(), dimensionsRequest.getDepth());
        detail.update(request.getWeight(), dimensions, request.getMaterials(),
            request.getCountryOfOrigin(), request.getWarrantyInfo(), request.getCareInstructions(),
            request.getAdditionalInfo());
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

    @Transactional(readOnly = true)
    @Override
    public ProductDetail getProductDetailByProductId(Long productId) {
        return productDetailRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));
    }
}
