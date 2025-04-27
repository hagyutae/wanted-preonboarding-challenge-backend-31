package com.mkhwang.wantedcqrs.product.controller;

import com.mkhwang.wantedcqrs.product.application.CategoryService;
import com.mkhwang.wantedcqrs.product.domain.dto.CategoryDto;
import com.mkhwang.wantedcqrs.product.domain.dto.CategorySearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping("/api/categories")
  public List<CategoryDto> getAllCategories(@RequestParam Integer level) {
    return categoryService.getAllCategories(level);
  }

  @GetMapping("/api/categories/{id}/products")
  public List<CategoryDto> searchCategories(@ModelAttribute CategorySearchDto searchDto) {
    return categoryService.searchCategories(searchDto);
  }
}
