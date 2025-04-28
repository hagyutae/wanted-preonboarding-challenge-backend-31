package com.mkhwang.wantedcqrs.product.application;

import com.mkhwang.wantedcqrs.config.GenericMapper;
import com.mkhwang.wantedcqrs.config.exception.ResourceNotFoundException;
import com.mkhwang.wantedcqrs.product.domain.ProductOption;
import com.mkhwang.wantedcqrs.product.domain.ProductOptionGroup;
import com.mkhwang.wantedcqrs.product.domain.dto.option.ProductOptionCreateRequestDto;
import com.mkhwang.wantedcqrs.product.domain.dto.option.ProductOptionCreateResponseDto;
import com.mkhwang.wantedcqrs.product.domain.dto.option.ProductOptionModifyRequestDto;
import com.mkhwang.wantedcqrs.product.infra.ProductOptionGroupRepository;
import com.mkhwang.wantedcqrs.product.infra.ProductOptionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptionService {
  private final ProductOptionRepository productOptionRepository;
  private final ProductOptionGroupRepository productOptionGroupRepository;
  private final GenericMapper genericMapper;

  @Transactional
  public ProductOptionCreateResponseDto addProductOption(Long productId, ProductOptionCreateRequestDto dto) {
    ProductOptionGroup optionGroup = productOptionGroupRepository.findById(dto.getOptionGroupId())
            .orElseThrow(() -> new ResourceNotFoundException("product.option.group.not.found"));
    if (!optionGroup.getProductId().equals(productId)) {
      throw new ResourceNotFoundException("product.option.group.not.found");
    }
    ProductOption productOption = genericMapper.toEntity(dto, ProductOption.class);
    productOption.setGroup(optionGroup);
    productOptionRepository.save(productOption);
    return genericMapper.toDto(productOption, ProductOptionCreateResponseDto.class);
  }

  @Transactional
  public void deleteProductOption(Long id, Long optionId) {
    ProductOption productOption = productOptionRepository.findById(optionId).orElseThrow(
            () -> new ResourceNotFoundException("product.option.not.found")
    );
    if (!productOption.getProductId().equals(id)) {
      throw new ResourceNotFoundException("product.option.not.found");
    }
    productOptionRepository.delete(productOption);
  }

  @Transactional
  public Object modifyProductOption(Long id, Long optionId, @Valid ProductOptionModifyRequestDto dto) {
    ProductOption productOption = productOptionRepository.findById(optionId).orElseThrow(
            () -> new ResourceNotFoundException("product.option.not.found")
    );
    if (!productOption.getProductId().equals(id)) {
      throw new ResourceNotFoundException("product.option.not.found");
    }

    productOption.setName(dto.getName());
    productOption.setAdditionalPrice(dto.getAdditionalPrice());
    productOption.setSku(dto.getSku());
    productOption.setStock(dto.getStock());
    productOption.setDisplayOrder(dto.getDisplayOrder());
    productOptionRepository.save(productOption);
    return genericMapper.toDto(productOption, ProductOption.class);

  }
}
