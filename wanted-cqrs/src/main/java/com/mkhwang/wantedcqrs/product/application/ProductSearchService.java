package com.mkhwang.wantedcqrs.product.application;


import com.mkhwang.wantedcqrs.product.domain.dto.ProductDto;
import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchDto;
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

  public Page<ProductDto> searchProducts(ProductSearchDto searchDto) {
    return productSearchRepository.searchProducts(searchDto);
  }
}
