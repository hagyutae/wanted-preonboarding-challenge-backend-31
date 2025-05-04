package investLee.platform.ecommerce.controller;

import investLee.platform.ecommerce.dto.response.CategoryTreeResponse;
import investLee.platform.ecommerce.dto.request.ProductSearchConditionRequest;
import investLee.platform.ecommerce.dto.response.ProductSummaryResponse;
import investLee.platform.ecommerce.service.CategoryService;
import investLee.platform.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<CategoryTreeResponse>> getCategoryTree() {
        return ResponseEntity.ok(categoryService.getCategoryTree());
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<Page<ProductSummaryResponse>> getProductsByCategory(
            @PathVariable Long categoryId,
            @Valid ProductSearchConditionRequest condition) {

        condition.setCategoryId(categoryId); // 🔥 category 고정
        Page<ProductSummaryResponse> result = productService.searchProducts(condition);
        return ResponseEntity.ok(result);
    }
}