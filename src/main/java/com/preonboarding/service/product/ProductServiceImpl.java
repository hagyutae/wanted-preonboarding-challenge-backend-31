package com.preonboarding.service.product;

import com.preonboarding.dto.request.ProductCreateRequestDto;
import com.preonboarding.dto.response.ProductResponse;
import com.preonboarding.global.response.BaseResponse;
import com.preonboarding.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public BaseResponse<ProductResponse> createProduct(ProductCreateRequestDto dto) {
        return null;
    }
}
