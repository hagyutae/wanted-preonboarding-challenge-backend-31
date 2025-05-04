package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductImage;
import com.wanted.ecommerce.product.dto.request.ProductImageRequest;
import com.wanted.ecommerce.product.dto.response.ProductImageCreateResponse;
import com.wanted.ecommerce.product.dto.response.ProductImageResponse;
import java.util.List;

public interface ProductImageService {

    ProductImageResponse createPrimaryProductImageResponse(Long productId);

    ProductImageCreateResponse createProductImage(long productId, ProductImageRequest imageRequest);

    List<ProductImageCreateResponse> createProductImages(Product product, List<ProductImageRequest> imageRequestList);

    void deleteProductImageByProductId(Long productId);

    List<ProductImageCreateResponse> createImageResponse(List<ProductImage> images);

}
