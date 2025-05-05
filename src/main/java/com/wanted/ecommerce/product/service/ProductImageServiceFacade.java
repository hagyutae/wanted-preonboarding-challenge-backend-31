package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductImage;
import com.wanted.ecommerce.product.dto.request.ProductRegisterRequest.ProductImageRequest;
import com.wanted.ecommerce.product.dto.response.ProductImageResponse;
import com.wanted.ecommerce.product.dto.response.ProductResponse.ProductImageCreateResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductImageServiceFacade {
    private final ProductImageService productImageService;

    public List<ProductImageCreateResponse> getProductImages(Product product, List<ProductImageRequest> requests){
        return productImageService.createProductImages(product, requests);
    }

    public ProductImageResponse getPrimaryProductImageResponse(Long productId){
        return productImageService.createPrimaryProductImageResponse(productId);
    }

    public List<ProductImageCreateResponse> getImageResponse(List<ProductImage> images){
        return productImageService.createImageResponse(images);
    }

    public void deleteProductImageByProductId(Long productId){
        productImageService.deleteProductImageByProductId(productId);
    }

}
