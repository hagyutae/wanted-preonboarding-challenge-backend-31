package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.product.infra.MainSearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MainSearchServiceTest {

  @Mock
  MainSearchRepository mainSearchRepository;

  @InjectMocks
  MainSearchService mainSearchService;

  @Test
  void test_getMainProduct() {
    // given
    // when
    mainSearchService.getMainProduct();

    // then
    verify(mainSearchRepository).searchMainPage();
  }
}