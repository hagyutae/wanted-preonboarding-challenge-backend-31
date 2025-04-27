package com.mkhwang.wantedcqrs.product.application;


import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchDetailDto;
import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchDto;
import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchResultDto;
import com.mkhwang.wantedcqrs.product.infra.ProductRepository;
import com.mkhwang.wantedcqrs.product.infra.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSearchService {
  private final ProductRepository productRepository;
  private final ProductSearchRepository productSearchRepository;

  public Page<ProductSearchResultDto> searchProducts(ProductSearchDto searchDto) {
    return productSearchRepository.searchProducts(searchDto);
  }

  public ProductSearchDetailDto getProductDetailById(Long id) {
//    throw new ResourceNotFoundException("product.detail.not.found", null);
    return null;
  }
}
