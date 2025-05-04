package com.preonboarding.service.user;

import com.preonboarding.global.code.ErrorCode;
import com.preonboarding.global.response.BaseException;
import com.preonboarding.global.response.ErrorResponseDto;
import com.preonboarding.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public void checkUserExists(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(false, ErrorResponseDto.of(ErrorCode.USER_NOT_FOUND)));
    }
}
