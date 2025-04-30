package com.example.wanted_preonboarding_challenge_backend_31.application.brand;

import com.example.wanted_preonboarding_challenge_backend_31.common.dto.ErrorInfo;
import com.example.wanted_preonboarding_challenge_backend_31.common.exception.CommonErrorType;
import com.example.wanted_preonboarding_challenge_backend_31.common.exception.CustomException;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.brand.Brand;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.brand.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BrandQueryService {

    private final BrandRepository brandRepository;

    public Brand getById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        ErrorInfo.of(CommonErrorType.RESOURCE_NOT_FOUND, "요청한 브랜드를 찾을 수 없습니다, ID:" + id)));
    }
}
