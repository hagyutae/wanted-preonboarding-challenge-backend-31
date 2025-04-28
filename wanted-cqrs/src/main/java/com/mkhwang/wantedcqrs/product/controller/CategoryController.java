package com.mkhwang.wantedcqrs.product.controller;

import com.mkhwang.wantedcqrs.config.advice.ApiMessage;
import com.mkhwang.wantedcqrs.product.application.CategoryService;
import com.mkhwang.wantedcqrs.product.domain.dto.category.CategoryDto;
import com.mkhwang.wantedcqrs.product.domain.dto.category.CategorySearchDto;
import com.mkhwang.wantedcqrs.product.domain.dto.category.CategorySearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @ApiMessage("category.all.success")
  @GetMapping("/api/categories")
  public List<CategoryDto> getAllCategories(@RequestParam(required = false) Integer level) {
    return categoryService.getAllCategories(level);
  }

  @ApiMessage("category.product.success")
  @GetMapping("/api/categories/{id}/products")
  public CategorySearchResultDto searchCategories(@PathVariable Long id, @ModelAttribute CategorySearchDto searchDto) {
    searchDto.setCategoryId(id);
    return categoryService.searchCategories(searchDto);
  }
}
