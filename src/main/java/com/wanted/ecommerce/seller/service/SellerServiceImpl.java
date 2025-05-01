package com.wanted.ecommerce.seller.service;

import com.wanted.ecommerce.common.exception.ErrorType;
import com.wanted.ecommerce.common.exception.ResourceNotFoundException;
import com.wanted.ecommerce.seller.domain.Seller;
import com.wanted.ecommerce.seller.dto.response.SellerDetailResponse;
import com.wanted.ecommerce.seller.dto.response.SellerResponse;
import com.wanted.ecommerce.seller.repository.SellerRepository;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    @Override
    public Seller getSellerById(Long sellerId) {
        return sellerRepository.findById(sellerId).orElseThrow(
            () -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND));
    }

    @Override
    public SellerResponse createSellerResponse(Long productId, String sellerName) {
        return SellerResponse.of(productId, sellerName);
    }

    @Override
    public SellerDetailResponse createSellerDetailResponse(Seller seller) {
        double sellerRating = seller.getRating()
            .setScale(1, RoundingMode.HALF_UP)
            .doubleValue();

        return SellerDetailResponse.of(seller.getId(),
            seller.getName(), seller.getDescription(), seller.getLogoUrl(), sellerRating,
            seller.getContactEmail(), seller.getContactPhone());
    }
}
