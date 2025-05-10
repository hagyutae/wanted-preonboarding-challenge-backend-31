package com.wanted.ecommerce.product.service.impl;

import com.wanted.ecommerce.common.dto.response.ProductItemResponse.ProductImageResponse;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductImage;
import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.dto.request.ProductRegisterRequest.ProductImageRequest;
import com.wanted.ecommerce.product.dto.response.ProductResponse.ProductImageCreateResponse;
import com.wanted.ecommerce.product.repository.ProductImageRepository;
import com.wanted.ecommerce.product.service.ProductImageService;
import com.wanted.ecommerce.product.service.ProductOptionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductOptionService optionService;

    @Transactional
    @Override
    public List<ProductImageCreateResponse> createProductImages(Product product,
        List<ProductImageRequest> imageRequestList) {
        List<ProductImage> images = imageRequestList.stream().map(imageRequest ->
        {
            ProductOption option = optionService.getOptionById(imageRequest.getOptionId());
            return ProductImage.of(product, imageRequest, option);
        }).toList();

        return productImageRepository.saveAll(images).stream().map(
            image -> ProductImageCreateResponse.of(image, image.getOption())).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ProductImageResponse createPrimaryProductImageResponse(Long productId) {
        return productImageRepository.findByProductIdAndPrimaryTrue(productId)
            .map(ProductImageResponse::of)
            .orElse(null);
    }

    @Transactional
    @Override
    public void deleteProductImageByProductId(Long productId) {
        productImageRepository.deleteByProductId(productId);
    }

    @Override
    public List<ProductImageCreateResponse> createImageResponse(List<ProductImage> images) {
        return images.stream()
            .map(image -> {
                if (image.getOption() != null) {
                    return ProductImageCreateResponse.of(image, image.getOption());
                }
                return ProductImageCreateResponse.of(image);
            }).toList();
    }
}
