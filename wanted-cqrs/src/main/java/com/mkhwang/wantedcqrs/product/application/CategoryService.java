package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.product.domain.dto.CategoryDto;
import com.mkhwang.wantedcqrs.product.domain.dto.CategorySearchDto;
import com.mkhwang.wantedcqrs.product.infra.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;

  public List<CategoryDto> getAllCategories(Integer level) {
    return null;
  }

  public List<CategoryDto> searchCategories(CategorySearchDto searchDto) {
    return null;
  }
}
