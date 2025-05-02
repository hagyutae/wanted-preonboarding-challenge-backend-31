package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductImage;
import com.wanted.ecommerce.product.domain.ProductOption;
import com.wanted.ecommerce.product.dto.request.ProductImageRequest;
import com.wanted.ecommerce.product.dto.response.ProductDetailImageResponse;
import com.wanted.ecommerce.product.dto.response.ProductImageCreateResponse;
import com.wanted.ecommerce.product.dto.response.ProductImageResponse;
import java.util.List;
import java.util.Optional;

public interface ProductImageService {

    List<ProductImage> createProductImages(Product product, List<ProductImageRequest> imageRequestList);

    ProductImageCreateResponse createProductImage(Product product, ProductOption option, ProductImageRequest imageRequest);

    Optional<ProductImage> getPrimaryProduct(Long productId);

    ProductImageResponse createPrimaryProductImageResponse(Long productId);

    void deleteProductImageByProductId(Long productId);

    List<ProductDetailImageResponse> createImageResponse(List<ProductImage> images);

}
