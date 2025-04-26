package com.mkhwang.wantedcqrs.product.infra;

import com.mkhwang.wantedcqrs.product.domain.dto.ProductDto;
import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchDto;
import org.springframework.data.domain.Page;

public interface ProductSearchRepository {
  Page<ProductDto> searchProducts(ProductSearchDto searchDto);
}
