package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductImage;
import com.wanted.ecommerce.product.dto.request.ProductImageRequest;
import com.wanted.ecommerce.product.dto.response.ProductDetailImageResponse;
import com.wanted.ecommerce.product.dto.response.ProductImageResponse;
import java.util.List;
import java.util.Optional;

public interface ProductImageService {

    List<ProductImage> saveProductImages(Product product, List<ProductImageRequest> imageRequestList);

    Optional<ProductImage> findPrimaryProduct(Long productId);

    ProductImageResponse createPrimaryProductImageResponse(Long productId);

    List<ProductDetailImageResponse> createImageResponse(List<ProductImage> images);

}
