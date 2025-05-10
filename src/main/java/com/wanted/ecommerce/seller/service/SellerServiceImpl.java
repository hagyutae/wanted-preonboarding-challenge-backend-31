package com.wanted.ecommerce.seller.service;

import com.wanted.ecommerce.common.dto.response.ProductItemResponse.SellerResponse;
import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.exception.ResourceNotFoundException;
import com.wanted.ecommerce.seller.domain.Seller;
import com.wanted.ecommerce.seller.dto.response.SellerDetailResponse;
import com.wanted.ecommerce.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    @Transactional(readOnly = true)
    @Override
    public Seller getSellerById(Long sellerId) {
        return sellerRepository.findById(sellerId).orElseThrow(
            () -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));
    }

    @Override
    public SellerResponse createSellerResponse(Seller seller) {
        return SellerResponse.of(seller);
    }

    @Override
    public SellerDetailResponse createSellerDetailResponse(Seller seller) {
        return SellerDetailResponse.of(seller);
    }
}
