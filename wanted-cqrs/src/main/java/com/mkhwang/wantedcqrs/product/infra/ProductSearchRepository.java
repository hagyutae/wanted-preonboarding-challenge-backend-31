package com.mkhwang.wantedcqrs.product.infra;

import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchDetailDto;
import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchDto;
import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchResultDto;
import com.mkhwang.wantedcqrs.product.domain.dto.TagDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductSearchRepository {
  Page<ProductSearchResultDto> searchProducts(ProductSearchDto searchDto);

  ProductSearchDetailDto getProductDetailBaseById(Long id);

  List<TagDto> findTagsByProductId(Long productId);
}
