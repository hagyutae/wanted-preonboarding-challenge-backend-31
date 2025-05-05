package com.wanted.ecommerce.seller.service;

import com.wanted.ecommerce.seller.domain.Seller;
import com.wanted.ecommerce.seller.dto.response.SellerDetailResponse;
import com.wanted.ecommerce.seller.dto.response.SellerResponse;

public interface SellerService {

    Seller getSellerById(Long sellerId);

    SellerResponse createSellerResponse(Seller seller);

    SellerDetailResponse createSellerDetailResponse(Seller seller);
}
