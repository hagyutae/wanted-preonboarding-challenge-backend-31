package minseok.cqrschallenge.product.service;

import minseok.cqrschallenge.common.dto.PaginationResponse;
import minseok.cqrschallenge.product.dto.request.ProductCreateRequest;
import minseok.cqrschallenge.product.dto.request.ProductUpdateRequest;
import minseok.cqrschallenge.product.dto.response.ProductCreateResponse;
import minseok.cqrschallenge.product.dto.response.ProductDetailResponse;
import minseok.cqrschallenge.product.dto.response.ProductListResponse;
import minseok.cqrschallenge.product.dto.response.ProductUpdateResponse;

public interface ProductService {

    ProductCreateResponse createProduct(ProductCreateRequest request);

    PaginationResponse<ProductListResponse> getProducts(
        int page, int perPage, String sort, String status,
        Integer minPrice, Integer maxPrice, String category,
        Integer seller, Integer brand, Boolean inStock, String search);

    ProductDetailResponse getProductDetail(Long id);

    ProductUpdateResponse updateProduct(Long id, ProductUpdateRequest request);

    void deleteProduct(Long id);
}