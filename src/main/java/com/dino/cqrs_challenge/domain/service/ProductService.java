package com.dino.cqrs_challenge.domain.service;

import com.dino.cqrs_challenge.domain.model.rq.CreateProductImageRequest;
import com.dino.cqrs_challenge.domain.model.rq.CreateProductOptionRequest;
import com.dino.cqrs_challenge.domain.model.rq.CreateProductRequest;
import com.dino.cqrs_challenge.domain.model.rq.UpdateProductOptionRequest;
import com.dino.cqrs_challenge.domain.model.rq.UpdateProductRequest;
import com.dino.cqrs_challenge.domain.model.rs.CreateProductImageResponse;
import com.dino.cqrs_challenge.domain.model.rs.CreateProductOptionResponse;
import com.dino.cqrs_challenge.domain.model.rs.CreateProductResponse;
import com.dino.cqrs_challenge.domain.model.rs.UpdateProductOptionResponse;
import com.dino.cqrs_challenge.domain.model.rs.UpdateProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    public CreateProductResponse createProduct(CreateProductRequest createProductRequest) {
        // TODO 상품 등록 로직 구현
        return new CreateProductResponse();
    }

    public UpdateProductResponse updateProduct(Integer id, UpdateProductRequest updateProductRequest) {
        // TODO 상품 수정 로직 구현
        return new UpdateProductResponse();
    }

    public void deleteProduct(Integer id) {
        // TODO 상품 삭제 로직 구현
    }

    public CreateProductOptionResponse addProductOption(Integer id, CreateProductOptionRequest createProductOptionRequest) {
        // TODO 상품 옵션 추가 로직 구현
        return new CreateProductOptionResponse();
    }

    public UpdateProductOptionResponse updateProductOption(Integer id, Integer optionId, UpdateProductOptionRequest updateProductOptionRequest) {
        // TODO 상품 옵션 수정 로직 구현
        return new UpdateProductOptionResponse();
    }

    public void deleteProductOption(Integer id, Integer optionId) {
        // TODO 상품 옵션 삭제 로직 구현
    }

    public CreateProductImageResponse addProductImage(Integer id, CreateProductImageRequest productImageRequest) {
        // TODO 상품 이미지 추가 로직 구현
        return new CreateProductImageResponse();
    }
}
