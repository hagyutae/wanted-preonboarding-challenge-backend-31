package com.mkhwang.wantedcqrs.product.application;


import com.mkhwang.wantedcqrs.product.domain.Product;
import com.mkhwang.wantedcqrs.product.domain.dto.ProductSearchDto;
import com.mkhwang.wantedcqrs.product.infra.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSearchService {
  private final ProductRepository productRepository;

  public Page<Product> searchProducts(ProductSearchDto searchDto) {
    return Page.empty();
  }
}
