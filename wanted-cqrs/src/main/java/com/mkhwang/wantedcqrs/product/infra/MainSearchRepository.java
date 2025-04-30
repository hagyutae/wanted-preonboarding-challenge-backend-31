package com.mkhwang.wantedcqrs.product.infra;

import com.mkhwang.wantedcqrs.product.domain.dto.main.MainResponseDto;

public interface MainSearchRepository {
  MainResponseDto searchMainPage();

}
