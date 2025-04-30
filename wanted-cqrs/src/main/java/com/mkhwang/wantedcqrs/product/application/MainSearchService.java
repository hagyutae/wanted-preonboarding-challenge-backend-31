package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.product.domain.dto.main.MainResponseDto;
import com.mkhwang.wantedcqrs.product.infra.MainSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainSearchService {
  private final MainSearchRepository mainSearchRepository;

  public MainResponseDto getMainProduct() {
    return mainSearchRepository.searchMainPage();
  }
}
