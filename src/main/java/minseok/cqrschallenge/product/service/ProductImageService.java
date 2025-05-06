package minseok.cqrschallenge.product.service;

import minseok.cqrschallenge.product.dto.request.ProductImageCreateRequest;
import minseok.cqrschallenge.product.dto.response.ProductImageResponse;

public interface ProductImageService {

    ProductImageResponse addProductImage(Long productId, ProductImageCreateRequest request);
}