package com.mkhwang.wantedcqrs.product.infra;

import com.mkhwang.wantedcqrs.product.domain.dto.category.CategorySearchDto;
import com.mkhwang.wantedcqrs.product.domain.dto.category.CategorySearchResultDto;

public interface CategorySearchRepository {
  CategorySearchResultDto searchCategories(CategorySearchDto searchDto);

}
