package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.config.GenericMapper;
import com.mkhwang.wantedcqrs.product.domain.Category;
import com.mkhwang.wantedcqrs.product.domain.dto.CategoryDto;
import com.mkhwang.wantedcqrs.product.domain.dto.CategorySearchDto;
import com.mkhwang.wantedcqrs.product.domain.dto.CategorySearchResultDto;
import com.mkhwang.wantedcqrs.product.infra.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final GenericMapper genericMapper;

  public List<CategoryDto> getAllCategories(Integer level) {
    List<Category> all = categoryRepository.findAll();
    List<CategoryDto> root = new ArrayList<>();

    for (Category category : all) {
      if (category.getLevel() == null || category.getLevel() == 1) {
        root.add(genericMapper.toDto(category, CategoryDto.class));
      } else {
        root.stream()
                .filter(c -> c.getId().equals(category.getParent().getId()))
                .findFirst().ifPresent(parent -> parent.addChildren(genericMapper.toDto(category, CategoryDto.class)));
      }
    }

    if (level != null) {
      return root.stream().filter(
                      c -> c.getLevel() != null && c.getLevel().equals(level))
              .toList();
    }

    return root;
  }

  public CategorySearchResultDto searchCategories(Long id, CategorySearchDto searchDto) {
    return null;
  }
}
