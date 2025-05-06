package com.sandro.wanted_shop.category.web;

import com.sandro.wanted_shop.category.CategoryDto;
import com.sandro.wanted_shop.category.CategoryService;
import com.sandro.wanted_shop.product.dto.ProductDto;
import com.sandro.wanted_shop.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/categories")
@RestController
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}/products")
    public List<ProductDto> getProductsByCategory(@PathVariable Long id) {
        return productService.getProductsByCategory(id);
    }
}
