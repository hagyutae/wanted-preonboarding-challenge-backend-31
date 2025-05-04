package investLee.platform.ecommerce.service;

import investLee.platform.ecommerce.domain.entity.ProductEntity;
import investLee.platform.ecommerce.domain.entity.ProductOptionEntity;
import investLee.platform.ecommerce.domain.entity.ProductOptionGroupEntity;
import investLee.platform.ecommerce.dto.request.ProductOptionGroupCreateRequest;
import investLee.platform.ecommerce.dto.request.ProductOptionUpdateRequest;
import investLee.platform.ecommerce.repository.ProductOptionGroupRepository;
import investLee.platform.ecommerce.repository.ProductOptionRepository;
import investLee.platform.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptionService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository optionRepository;
    private final ProductOptionGroupRepository optionGroupRepository;

    @Transactional
    public void updateOption(Long productId, Long optionId, ProductOptionUpdateRequest dto) {
        ProductOptionEntity option = optionRepository.findById(optionId)
                .orElseThrow(() -> new EntityNotFoundException("옵션 없음"));

        if (!option.getOptionGroup().getProduct().getId().equals(productId)) {
            throw new IllegalStateException("상품에 속한 옵션이 아닙니다.");
        }

        option.update(dto);
    }

    @Transactional
    public void deleteOption(Long productId, Long optionId) {
        ProductOptionEntity option = optionRepository.findById(optionId)
                .orElseThrow(() -> new EntityNotFoundException("옵션 없음"));

        if (!option.getOptionGroup().getProduct().getId().equals(productId)) {
            throw new IllegalStateException("상품에 속한 옵션이 아닙니다.");
        }

        optionRepository.delete(option);
    }

    @Transactional
    public void addOptionGroup(Long productId, ProductOptionGroupCreateRequest dto) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("상품 없음"));

        ProductOptionGroupEntity group = ProductOptionGroupEntity.builder()
                .product(product)
                .name(dto.getName())
                .displayOrder(dto.getDisplayOrder())
                .build();

        optionGroupRepository.save(group);

        for (ProductOptionGroupCreateRequest.ProductOptionCreateRequestDTO optDTO : dto.getOptions()) {
            ProductOptionEntity option = ProductOptionEntity.builder()
                    .optionGroup(group)
                    .name(optDTO.getName())
                    .additionalPrice(optDTO.getAdditionalPrice())
                    .sku(optDTO.getSku())
                    .stock(optDTO.getStock())
                    .displayOrder(optDTO.getDisplayOrder())
                    .build();

            optionRepository.save(option);
        }
    }
}