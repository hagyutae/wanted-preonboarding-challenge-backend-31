package com.preonboarding.service.seller;

import com.preonboarding.domain.Seller;
import com.preonboarding.global.code.ErrorCode;
import com.preonboarding.global.response.BaseException;
import com.preonboarding.global.response.ErrorResponseDto;
import com.preonboarding.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;

    @Override
    @Transactional(readOnly = true)
    public Seller getSeller(Long id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new BaseException(false, ErrorResponseDto.of(ErrorCode.SELLER_NOT_FOUND)));
    }
}
