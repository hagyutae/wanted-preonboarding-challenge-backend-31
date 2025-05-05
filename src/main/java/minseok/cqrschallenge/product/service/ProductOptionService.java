package minseok.cqrschallenge.product.service;

import minseok.cqrschallenge.product.dto.request.ProductOptionCreateRequest;
import minseok.cqrschallenge.product.dto.response.ProductOptionResponse;

public interface ProductOptionService {
    ProductOptionResponse addProductOption(Long productId, ProductOptionCreateRequest request);
    
    ProductOptionResponse updateProductOption(Long productId, Long optionId, ProductOptionCreateRequest request);
    
    void deleteProductOption(Long productId, Long optionId);
}