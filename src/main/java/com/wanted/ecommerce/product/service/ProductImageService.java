package com.wanted.ecommerce.product.service;

import com.wanted.ecommerce.common.dto.response.ProductItemResponse.ProductImageResponse;
import com.wanted.ecommerce.product.domain.Product;
import com.wanted.ecommerce.product.domain.ProductImage;
import com.wanted.ecommerce.product.dto.request.ProductRegisterRequest.ProductImageRequest;
import com.wanted.ecommerce.product.dto.response.ProductResponse.ProductImageCreateResponse;
import java.util.List;

public interface ProductImageService {

    ProductImageResponse createPrimaryProductImageResponse(Long productId);

    List<ProductImageCreateResponse> createProductImages(Product product, List<ProductImageRequest> imageRequestList);

    void deleteProductImageByProductId(Long productId);

    List<ProductImageCreateResponse> createImageResponse(List<ProductImage> images);

}
