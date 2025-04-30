package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.config.GenericMapper;
import com.mkhwang.wantedcqrs.product.domain.dto.category.CategorySearchDto;
import com.mkhwang.wantedcqrs.product.infra.CategoryRepository;
import com.mkhwang.wantedcqrs.product.infra.impl.CategorySearchRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
  @Mock
  CategoryRepository categoryRepository;
  @Mock
  GenericMapper genericMapper;
  @Mock
  CategorySearchRepositoryImpl categorySearchRepository;
  @InjectMocks
  CategoryService categoryService;

  @Test
  void test_searchCategories() {
    // given
    CategorySearchDto searchDto = new CategorySearchDto();

    // when
    categoryService.searchCategories(searchDto);

    // then
    verify(categorySearchRepository).searchCategories(searchDto);
  }
}