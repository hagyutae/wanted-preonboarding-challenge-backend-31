package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.config.GenericMapper;
import com.mkhwang.wantedcqrs.config.exception.ResourceNotFoundException;
import com.mkhwang.wantedcqrs.product.domain.Product;
import com.mkhwang.wantedcqrs.product.domain.ProductImage;
import com.mkhwang.wantedcqrs.product.domain.ProductOption;
import com.mkhwang.wantedcqrs.product.domain.dto.product.ProductImageCreateDto;
import com.mkhwang.wantedcqrs.product.domain.dto.product.ProductImageCreateResponseDto;
import com.mkhwang.wantedcqrs.product.infra.ProductImageRepository;
import com.mkhwang.wantedcqrs.product.infra.ProductOptionRepository;
import com.mkhwang.wantedcqrs.product.infra.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductImageService {
  private final ProductImageRepository productImageRepository;
  private final ProductRepository productRepository;
  private final GenericMapper genericMapper;
  private final ProductOptionRepository productOptionRepository;

  @Transactional
  public ProductImageCreateResponseDto addProductImage(Long id, ProductImageCreateDto dto) {
    Product product = productRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("product.not.found")
    );
    ProductOption productOption = null;
    if (dto.getOptionId() != null) {
      productOption = productOptionRepository.findById(dto.getOptionId()).orElseThrow(
              () -> new ResourceNotFoundException("product.option.not.found")
      );
    }

    ProductImage productImage = genericMapper.toEntity(dto, ProductImage.class);
    productImage.setProduct(product);
    if (productOption != null) {
      productImage.setOption(productOption);
    }

    productImageRepository.save(productImage);
    return genericMapper.toDto(productImage, ProductImageCreateResponseDto.class);
  }
}
