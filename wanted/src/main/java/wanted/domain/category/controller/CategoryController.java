package wanted.domain.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wanted.common.response.SuccessResponse;
import wanted.domain.category.service.CategoryService;


@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<?> getCategories(@RequestParam(required = false) Integer level) {
        return ResponseEntity.ok(SuccessResponse.ok(categoryService.getCategoryTree(level)));
    }
}
