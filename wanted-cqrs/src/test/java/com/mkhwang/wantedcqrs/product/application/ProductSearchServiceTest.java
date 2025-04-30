package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.config.GenericMapper;
import com.mkhwang.wantedcqrs.product.domain.ProductDetail;
import com.mkhwang.wantedcqrs.product.domain.ProductImage;
import com.mkhwang.wantedcqrs.product.domain.dto.*;
import com.mkhwang.wantedcqrs.product.infra.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductSearchServiceTest {

  @Mock
  ProductSearchRepository productSearchRepository;
  @Mock
  ReviewSearchRepository reviewSearchRepository;
  @Mock
  ProductDetailRepository productDetailRepository;
  @Mock
  CategoryRepository categoryRepository;
  @Mock
  ProductCategoryRepository productCategoryRepository;
  @Mock
  ProductImageRepository productImageRepository;
  @Mock
  GenericMapper genericMapper;
  @InjectMocks
  ProductSearchService productSearchService;

  @DisplayName("searchProducts() 메서드 테스트")
  @Test
  void test_searchProduct() {
    // given
    ProductSearchDto dto = mock(ProductSearchDto.class);

    // when
    productSearchService.searchProducts(dto);

    // then
    verify(productSearchRepository).searchProducts(dto);
  }

  @DisplayName("getProductDetailById() 메서드 테스트")
  @Test
  void test_getProductDetailById() {
    // given
    Long id = 1L;
    ProductSearchDetailDto base = mock(ProductSearchDetailDto.class);
    ProductRatingDto rating = mock(ProductRatingDto.class);
    ProductDetail detail = mock(ProductDetail.class);
    ProductDetailDto detailDto = mock(ProductDetailDto.class);
    List<ProductImage> productImages = List.of(mock(ProductImage.class));
    List<ProductCategoryDto> categories = List.of(mock(ProductCategoryDto.class));
    List<ProductImageDto> imageDtoList = List.of(mock(ProductImageDto.class));
    List<TagDto> tags = List.of(mock(TagDto.class));
    given(productSearchRepository.getProductDetailBaseById(id)).willReturn(base);
    given(reviewSearchRepository.findAverageRatingByProductId(id)).willReturn(rating);
    given(productDetailRepository.findByProductId(id)).willReturn(Optional.of(detail));
    given(genericMapper.toDto(detail, ProductDetailDto.class)).willReturn(detailDto);
    given(productImageRepository.findByProductId(id)).willReturn(productImages);
    given(productSearchRepository.findTagsByProductId(id)).willReturn(tags);

    // when
    ProductSearchDetailDto result = productSearchService.getProductDetailById(id);

    // then
    verify(productSearchRepository).getProductDetailBaseById(id);
    verify(reviewSearchRepository).findAverageRatingByProductId(id);
    verify(productDetailRepository).findByProductId(id);
    verify(productCategoryRepository).findByProductId(id);
    verify(categoryRepository).findAll();
    verify(productImageRepository).findByProductId(id);
    verify(productSearchRepository).findTagsByProductId(id);

    assertEquals(base, result);

  }
}