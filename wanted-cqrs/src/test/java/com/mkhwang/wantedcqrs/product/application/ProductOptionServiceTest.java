package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.config.GenericMapper;
import com.mkhwang.wantedcqrs.product.domain.ProductOption;
import com.mkhwang.wantedcqrs.product.domain.ProductOptionGroup;
import com.mkhwang.wantedcqrs.product.domain.dto.option.ProductOptionCreateRequestDto;
import com.mkhwang.wantedcqrs.product.domain.dto.option.ProductOptionModifyRequestDto;
import com.mkhwang.wantedcqrs.product.infra.ProductOptionGroupRepository;
import com.mkhwang.wantedcqrs.product.infra.ProductOptionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductOptionServiceTest {

  @Mock
  ProductOptionRepository productOptionRepository;
  @Mock
  ProductOptionGroupRepository productOptionGroupRepository;
  @Mock
  GenericMapper genericMapper;
  @InjectMocks
  ProductOptionService productOptionService;

  @DisplayName("상품 옵션을 추가한다.")
  @Test
  void test_addProductOption() {
    // given
    Long productId = 1L;
    ProductOption productOption = mock(ProductOption.class);
    ProductOptionCreateRequestDto dto = mock(ProductOptionCreateRequestDto.class);
    ProductOptionGroup optionGroup = mock(ProductOptionGroup.class);
    given(dto.getOptionGroupId()).willReturn(1L);
    given(productOptionGroupRepository.findById(dto.getOptionGroupId())).willReturn(Optional.of(optionGroup));
    given(optionGroup.getProductId()).willReturn(productId);
    given(genericMapper.toEntity(dto, ProductOption.class)).willReturn(productOption);

    // when
    productOptionService.addProductOption(productId, dto);

    // then
    verify(productOptionRepository).save(productOption);
  }

  @DisplayName("상품 옵션을 삭제한다.")
  @Test
  void test_deleteProductOption() {
    // given
    Long productId = 1L;
    Long optionId = 2L;
    ProductOption productOption = mock(ProductOption.class);
    given(productOptionRepository.findById(optionId)).willReturn(Optional.of(productOption));
    given(productOption.getProductId()).willReturn(productId);

    // when
    productOptionService.deleteProductOption(productId, optionId);

    // then
    verify(productOptionRepository).delete(productOption);
  }

  @DisplayName("상품 옵션을 수정한다.")
  @Test
  void test_modifyProductOption() {
    // given
    Long productId = 1L;
    Long optionId = 2L;
    ProductOption productOption = mock(ProductOption.class);
    ProductOptionModifyRequestDto dto = mock(ProductOptionModifyRequestDto.class);
    given(productOptionRepository.findById(optionId)).willReturn(Optional.of(productOption));
    given(productOption.getProductId()).willReturn(productId);
    given(dto.getName()).willReturn("name");
    given(dto.getAdditionalPrice()).willReturn(BigDecimal.ONE);
    given(dto.getSku()).willReturn("sku");
    given(dto.getStock()).willReturn(1);
    given(dto.getDisplayOrder()).willReturn(1);

    // when
    productOptionService.modifyProductOption(productId, optionId, dto);

    // then
    verify(productOption).setName(dto.getName());
    verify(productOption).setAdditionalPrice(dto.getAdditionalPrice());
    verify(productOption).setSku(dto.getSku());
    verify(productOption).setStock(dto.getStock());
    verify(productOption).setDisplayOrder(dto.getDisplayOrder());
    verify(productOptionRepository).save(productOption);
  }
}