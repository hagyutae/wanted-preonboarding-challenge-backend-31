package com.example.demo.productoption.service;

import com.example.demo.common.exception.ErrorCode;
import com.example.demo.common.exception.GlobalException;
import com.example.demo.productoption.controller.request.AddProductOptionRequest;
import com.example.demo.productoption.controller.request.UpdateProductOptionRequest;
import com.example.demo.productoption.entity.ProductOptionEntity;
import com.example.demo.productoption.entity.ProductOptionGroupEntity;
import com.example.demo.productoption.dto.ProductAddResult;
import com.example.demo.productoption.dto.ProductUpdateResult;
import com.example.demo.productoption.repository.ProductOptionGroupRepository;
import com.example.demo.productoption.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.productoption.ProductOptionError.PRODUCT_OPTION_ADD_FAIL;
import static com.example.demo.productoption.ProductOptionError.PRODUCT_OPTION_UPDATE_FAIL;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductOptionService {
    private final ProductOptionRepository productOptionRepository;
    private final ProductOptionGroupRepository productOptionGroupRepository;


    public ProductAddResult addOption(Long productId, AddProductOptionRequest request) {
        ProductOptionGroupEntity productOptionGroupEntity = productOptionGroupRepository.findByIdAndProductId(request.optionGroupId(), productId)
                .orElseThrow(() -> new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, PRODUCT_OPTION_ADD_FAIL));
        ProductOptionEntity productOptionEntity = ProductOptionEntity.create(request, productOptionGroupEntity);

        ProductOptionEntity save = productOptionRepository.save(productOptionEntity);

        return new ProductAddResult(
                save.getId(),
                save.getProductOptionGroupEntity().getId(),
                save.getName(),
                save.getAdditionalPrice(),
                save.getSku(),
                save.getStock(),
                save.getDisplayOrder()
        );
    }

    public ProductUpdateResult update(Long productId, Long optionId, UpdateProductOptionRequest request) {
        ProductOptionEntity productOptionEntity = productOptionRepository.findByIdAndProductId(optionId, productId)
                .orElseThrow(() -> new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, PRODUCT_OPTION_UPDATE_FAIL));

        productOptionEntity.update(request);

        ProductOptionEntity save = productOptionRepository.save(productOptionEntity);

        return new ProductUpdateResult(
                save.getId(),
                save.getProductOptionGroupEntity().getId(),
                save.getName(),
                save.getAdditionalPrice(),
                save.getSku(),
                save.getStock(),
                save.getDisplayOrder()
        );
    }

    public void delete(Long productId, Long optionId) {
        ProductOptionEntity productOptionEntity = productOptionRepository.findByIdAndProductId(optionId, productId)
                .orElseThrow(() -> new GlobalException(ErrorCode.RESOURCE_NOT_FOUND, PRODUCT_OPTION_UPDATE_FAIL));

        productOptionRepository.delete(productOptionEntity);
    }
}
