package com.mkhwang.wantedcqrs.product.infra;

import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchDto;
import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchResultDto;
import org.springframework.data.domain.Page;

public interface ProductSearchRepository {
  Page<ProductSearchResultDto> searchProducts(ProductSearchDto searchDto);
}
