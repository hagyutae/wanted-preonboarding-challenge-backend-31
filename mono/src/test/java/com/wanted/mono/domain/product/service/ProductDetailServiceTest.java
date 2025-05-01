package com.wanted.mono.domain.product.service;

import com.wanted.mono.domain.product.dto.AdditionalInfo;
import com.wanted.mono.domain.product.dto.Dimension;
import com.wanted.mono.domain.product.dto.ProductDetailRequest;
import com.wanted.mono.domain.product.entity.Product;
import com.wanted.mono.domain.product.entity.ProductDetail;
import com.wanted.mono.domain.product.repository.ProductDetailRepository;
import com.wanted.mono.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductDetailServiceTest {
    @Mock
    private ProductDetailRepository productDetailRepository;

    @Mock
    private ProductRepository productRepository;
    
    @Autowired
    private ProductDetailService productDetailService;

    @Test
    @DisplayName("상품 디테일 저장 테스트")
    void createProductDetail() {
        // given
        Long productId = 1L;
        Product product = mock(Product.class);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        
        ProductDetailRequest request = ProductDetailRequest.builder()
                .weight(new BigDecimal("25.5"))
                .dimensions(new Dimension(200, 85, 90))
                .materials("가죽, 목재, 폼")
                .countryOfOrigin("대한민국")
                .warrantyInfo("2년 품질 보증")
                .careInstructions("마른 천으로 표면을 닦아주세요")
                .additionalInfo(new AdditionalInfo(true, "30분"))
                .build();
        
        ProductDetail productDetail = ProductDetail.of(request);
        when(productDetailRepository.save(any(ProductDetail.class))).thenReturn(productDetail);
        
        // when
        Long productDetailId = productDetailService.createProductDetail(request, product);
        
        // then
        assertThat(productDetailId).isNotNull();
        verify(productDetailRepository).save(any(ProductDetail.class));
    }
}