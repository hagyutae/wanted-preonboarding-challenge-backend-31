package com.example.wanted_preonboarding_challenge_backend_31.application.product;

import com.example.wanted_preonboarding_challenge_backend_31.common.dto.ErrorInfo;
import com.example.wanted_preonboarding_challenge_backend_31.common.exception.CommonErrorType;
import com.example.wanted_preonboarding_challenge_backend_31.common.exception.CustomException;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.Product;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductOption;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.product.ProductOptionGroup;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.ProductCategoryRepository;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.ProductImageRepository;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.ProductOptionGroupRepository;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.ProductOptionRepository;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.ProductRepository;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.product.ProductTagRepository;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.category.CategoryDetailDto;
import com.example.wanted_preonboarding_challenge_backend_31.shared.dto.product.ProductImageDetailDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductQueryService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductTagRepository productTagRepository;

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        ErrorInfo.of(CommonErrorType.RESOURCE_NOT_FOUND, "요청한 상품을 찾을 수 없습니다, ID:" + id)));
    }

    public ProductOption getProductOptionById(Long id) {
        return productOptionRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        ErrorInfo.of(CommonErrorType.RESOURCE_NOT_FOUND, "요청한 옵션을 찾을 수 없습니다, ID:" + id)));
    }

    public ProductOptionGroup getProductOptionGroupByIdAndProductId(Long id, Long productId) {
        return productOptionGroupRepository.findByIdAndProductId(id, productId)
                .orElseThrow(() -> new CustomException(
                        ErrorInfo.of(CommonErrorType.RESOURCE_NOT_FOUND, "요청한 옵션 그룹을 찾을 수 없습니다, ID:" + id)));
    }

    public ProductOption getProductOptionByIdAndProductId(Long id, Long productId) {
        return productOptionRepository.findByIdAndProductOptionGroup_Product_Id(id, productId)
                .orElseThrow(() -> new CustomException(
                        ErrorInfo.of(CommonErrorType.RESOURCE_NOT_FOUND, "요청한 옵션을 찾을 수 없습니다, ID:" + id)));
    }

    public Long getProductOptionGroupIdByProductOptionId(Long optionId) {
        return productOptionRepository.findProductOptionGroupIdById(optionId);
    }

    public List<CategoryDetailDto> getAllProductCategoryByProductId(Long productId) {
        return productCategoryRepository.findAllByProductId(productId).stream()
                .map(CategoryDetailDto::from)
                .toList();
    }

    public List<ProductImageDetailDto> getAllProductImageByProductId(Long productId) {
        return productImageRepository.findAllByProductId(productId).stream()
                .map(ProductImageDetailDto::from)
                .toList();
    }

    public List<Long> getAllProductTagIdByProductId(Long productId) {
        return productTagRepository.findAllIdsByProductId(productId);
    }
}
