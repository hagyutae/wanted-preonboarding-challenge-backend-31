package minseok.cqrschallenge.category.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import minseok.cqrschallenge.category.dto.response.CategoryProductsResponse;
import minseok.cqrschallenge.category.dto.response.CategoryResponse;
import minseok.cqrschallenge.category.service.CategoryService;
import minseok.cqrschallenge.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategories(
        @RequestParam(required = false) Integer level) {

        List<CategoryResponse> categories = categoryService.getCategories(level);

        return ResponseEntity.ok(ApiResponse.success(
            categories,
            "카테고리 목록을 성공적으로 조회했습니다."
        ));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<ApiResponse<CategoryProductsResponse>> getCategoryProducts(
        @PathVariable Long id,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int perPage,
        @RequestParam(defaultValue = "created_at:desc") String sort,
        @RequestParam(defaultValue = "true") boolean includeSubcategories) {

        CategoryProductsResponse response = categoryService.getCategoryProducts(
            id, page, perPage, sort, includeSubcategories);

        return ResponseEntity.ok(ApiResponse.success(
            response,
            "카테고리 상품 목록을 성공적으로 조회했습니다."
        ));
    }
}