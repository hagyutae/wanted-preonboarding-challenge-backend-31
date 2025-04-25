package com.example.wanted_preonboarding_challenge_backend_31.application.seller;

import com.example.wanted_preonboarding_challenge_backend_31.common.dto.ErrorInfo;
import com.example.wanted_preonboarding_challenge_backend_31.common.exception.CommonErrorType;
import com.example.wanted_preonboarding_challenge_backend_31.common.exception.CustomException;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.seller.Seller;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.seller.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SellerQueryService {

    private final SellerRepository sellerRepository;

    public Seller getById(Long id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        ErrorInfo.of(CommonErrorType.RESOURCE_NOT_FOUND, "요청한 판매자를 찾을 수 없습니다, ID:" + id)));
    }
}
