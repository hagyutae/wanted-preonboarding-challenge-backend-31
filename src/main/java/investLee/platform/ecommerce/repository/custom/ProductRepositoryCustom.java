package investLee.platform.ecommerce.repository.custom;

import investLee.platform.ecommerce.dto.request.ProductSearchConditionRequest;
import investLee.platform.ecommerce.dto.request.ProductSearchRequest;
import investLee.platform.ecommerce.dto.response.ProductSummaryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductRepositoryCustom {
    Page<ProductSummaryResponse> searchProducts(ProductSearchConditionRequest condition);
    Page<ProductSummaryResponse> searchByKeyword(ProductSearchRequest dto);
    List<ProductSummaryResponse> findRelatedProducts(Long productId, int limit);
}