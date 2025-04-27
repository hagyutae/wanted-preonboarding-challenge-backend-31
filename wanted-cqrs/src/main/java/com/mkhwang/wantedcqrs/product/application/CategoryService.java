package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.product.infra.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;
}
