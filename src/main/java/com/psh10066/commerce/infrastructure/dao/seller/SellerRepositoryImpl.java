package com.psh10066.commerce.infrastructure.dao.seller;

import com.psh10066.commerce.domain.exception.ResourceNotFoundException;
import com.psh10066.commerce.domain.model.seller.Seller;
import com.psh10066.commerce.domain.model.seller.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SellerRepositoryImpl implements SellerRepository {

    private final SellerJpaRepository sellerJpaRepository;

    @Override
    public Seller getById(Long id) {
        return sellerJpaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Seller", id));
    }
}
