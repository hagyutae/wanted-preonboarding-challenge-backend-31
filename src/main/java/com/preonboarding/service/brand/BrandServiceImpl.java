package com.preonboarding.service.brand;

import com.preonboarding.domain.Brand;
import com.preonboarding.global.code.ErrorCode;
import com.preonboarding.global.response.BaseException;
import com.preonboarding.global.response.ErrorResponseDto;
import com.preonboarding.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    @Transactional(readOnly = true)
    public Brand getBrand(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new BaseException(false, ErrorResponseDto.of(ErrorCode.BRAND_NOT_FOUND)));
    }
}
