package com.wanted.ecommerce.product.service.impl;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductImage;
import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.dto.request.ProductImageRequest;
import com.wanted.ecommerce.product.dto.response.ProductDetailImageResponse;
import com.wanted.ecommerce.product.dto.response.ProductImageResponse;
import com.wanted.ecommerce.product.repository.ProductImageRepository;
import com.wanted.ecommerce.product.service.ProductImageService;
import com.wanted.ecommerce.product.service.ProductOptionService;
import java.util.List;
import java.util.Optional;
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
    public List<ProductImage> saveProductImages(Product product, List<ProductImageRequest> imageRequestList) {
        List<ProductImage> images = imageRequestList.stream().map(imageRequest ->
        {
            ProductOption option = optionService.findOptionById(imageRequest.getOptionId());
            return ProductImage.of(product, imageRequest.getUrl(), imageRequest.getAltText(),
                imageRequest.getIsPrimary(), imageRequest.getDisplayOrder(), option);
        }).toList();
        return productImageRepository.saveAll(images);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ProductImage> findPrimaryProduct(Long productId) {
        return productImageRepository.findByProductIdAndPrimaryTrue(productId);
    }

    @Override
    public ProductImageResponse createPrimaryProductImageResponse(Long productId){
        return findPrimaryProduct(productId)
            .map(image -> ProductImageResponse.of(image.getUrl(), image.getAltText()))
            .orElse(null);
    }

    @Transactional
    @Override
    public void deleteProductImageByProductId(Long productId) {
        productImageRepository.deleteByProductId(productId);
    }

    @Override
    public List<ProductDetailImageResponse> createImageResponse(List<ProductImage> images) {
        return images.stream()
            .map(image -> {
                if(image.getOption() != null){
                    return ProductDetailImageResponse.of(image.getId(), image.getUrl(),
                        image.getAltText(), image.isPrimary(), image.getDisplayOrder(),
                        image.getOption().getId());
                }
                return ProductDetailImageResponse.of(image.getId(), image.getUrl(),
                    image.getAltText(), image.isPrimary(), image.getDisplayOrder());
            }).toList();
    }
}
