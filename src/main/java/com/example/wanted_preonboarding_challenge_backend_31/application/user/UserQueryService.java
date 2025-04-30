package com.example.wanted_preonboarding_challenge_backend_31.application.user;

import com.example.wanted_preonboarding_challenge_backend_31.common.dto.ErrorInfo;
import com.example.wanted_preonboarding_challenge_backend_31.common.exception.CommonErrorType;
import com.example.wanted_preonboarding_challenge_backend_31.common.exception.CustomException;
import com.example.wanted_preonboarding_challenge_backend_31.domain.model.user.User;
import com.example.wanted_preonboarding_challenge_backend_31.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public User getRandomOne() {
        return userRepository.findRandomOne()
                .orElseThrow(
                        () -> new CustomException(ErrorInfo.of(CommonErrorType.RESOURCE_NOT_FOUND, "등록된 유저가 없습니다.")));
    }
}
