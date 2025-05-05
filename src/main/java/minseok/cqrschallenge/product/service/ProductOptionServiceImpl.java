package minseok.cqrschallenge.product.service;

import lombok.RequiredArgsConstructor;
import minseok.cqrschallenge.common.exception.ResourceNotFoundException;
import minseok.cqrschallenge.product.dto.request.ProductOptionCreateRequest;
import minseok.cqrschallenge.product.dto.response.ProductOptionResponse;
import minseok.cqrschallenge.product.entity.Product;
import minseok.cqrschallenge.product.entity.ProductOption;
import minseok.cqrschallenge.product.entity.ProductOptionGroup;
import minseok.cqrschallenge.product.repository.ProductOptionGroupRepository;
import minseok.cqrschallenge.product.repository.ProductOptionRepository;
import minseok.cqrschallenge.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {

    private final ProductRepository productRepository;
    private final ProductOptionGroupRepository optionGroupRepository;
    private final ProductOptionRepository optionRepository;

    @Override
    @Transactional
    public ProductOptionResponse addProductOption(Long productId, ProductOptionCreateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("요청한 상품을 찾을 수 없습니다."));
        
        ProductOptionGroup optionGroup = optionGroupRepository.findById(request.getOptionGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("요청한 옵션 그룹을 찾을 수 없습니다."));
        
        if (!optionGroup.getProduct().getId().equals(productId)) {
            throw new ResourceNotFoundException("해당 상품에 속한 옵션 그룹이 아닙니다.");
        }
        
        ProductOption option = ProductOption.builder()
                .optionGroup(optionGroup)
                .name(request.getName())
                .additionalPrice(request.getAdditionalPrice())
                .sku(request.getSku())
                .stock(request.getStock())
                .displayOrder(request.getDisplayOrder())
                .build();
        
        ProductOption savedOption = optionRepository.save(option);
        
        return convertToResponse(savedOption);
    }

    @Override
    @Transactional
    public ProductOptionResponse updateProductOption(Long productId, Long optionId, ProductOptionCreateRequest request) {
        ProductOption option = optionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("요청한 옵션을 찾을 수 없습니다."));
        
        if (!option.getOptionGroup().getProduct().getId().equals(productId)) {
            throw new ResourceNotFoundException("해당 상품에 속한 옵션이 아닙니다.");
        }
        option.update(
            request.getName(),
            request.getAdditionalPrice(),
            request.getSku(),
            request.getStock(),
            request.getDisplayOrder()
        );
        ProductOption updatedOption = optionRepository.save(option);
        
        return convertToResponse(updatedOption);
    }

    @Override
    @Transactional
    public void deleteProductOption(Long productId, Long optionId) {
        ProductOption option = optionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("요청한 옵션을 찾을 수 없습니다."));
        
        if (!option.getOptionGroup().getProduct().getId().equals(productId)) {
            throw new ResourceNotFoundException("해당 상품에 속한 옵션이 아닙니다.");
        }
        
        optionRepository.delete(option);
    }
    
    private ProductOptionResponse convertToResponse(ProductOption option) {
        return ProductOptionResponse.builder()
                .id(option.getId())
                .optionGroupId(option.getOptionGroup().getId())
                .name(option.getName())
                .additionalPrice(option.getAdditionalPrice())
                .sku(option.getSku())
                .stock(option.getStock())
                .displayOrder(option.getDisplayOrder())
                .build();
    }
}