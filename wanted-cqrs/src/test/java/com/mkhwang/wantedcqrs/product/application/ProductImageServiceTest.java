package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.config.GenericMapper;
import com.mkhwang.wantedcqrs.product.domain.Product;
import com.mkhwang.wantedcqrs.product.domain.ProductImage;
import com.mkhwang.wantedcqrs.product.domain.ProductOption;
import com.mkhwang.wantedcqrs.product.domain.dto.product.ProductImageCreateDto;
import com.mkhwang.wantedcqrs.product.infra.ProductImageRepository;
import com.mkhwang.wantedcqrs.product.infra.ProductOptionRepository;
import com.mkhwang.wantedcqrs.product.infra.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductImageServiceTest {
  @Mock
  ProductImageRepository productImageRepository;
  @Mock
  ProductRepository productRepository;
  @Mock
  GenericMapper genericMapper;
  @Mock
  ProductOptionRepository productOptionRepository;
  @InjectMocks
  ProductImageService productImageService;

  @DisplayName("상품 이미지를 추가한다.")
  @Test
  void test_addProductImage() {
    // given
    Long productId = 1L;
    Product product = mock(Product.class);
    ProductImage image = mock(ProductImage.class);
    ProductOption productOption = mock(ProductOption.class);
    ProductImageCreateDto dto = mock(ProductImageCreateDto.class);
    given(dto.getOptionId()).willReturn(1L);
    given(productRepository.findById(productId)).willReturn(Optional.of(product));
    given(productOptionRepository.findById(dto.getOptionId())).willReturn(Optional.of(productOption));
    given(genericMapper.toEntity(dto, ProductImage.class)).willReturn(image);

    // when
    productImageService.addProductImage(productId, dto);

    // then
    verify(productImageRepository).save(image);
  }
}