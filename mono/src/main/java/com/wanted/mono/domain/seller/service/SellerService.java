package com.wanted.mono.domain.seller.service;

import com.wanted.mono.domain.seller.entity.Seller;
import com.wanted.mono.domain.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerService {
    private final SellerRepository sellerRepository;

    public Seller findById(Long sellerId) {
        return sellerRepository.findById(sellerId).orElse(null);
    }
}
