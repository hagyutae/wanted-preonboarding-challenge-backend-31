package com.mkhwang.wantedcqrs.user.application;

import com.mkhwang.wantedcqrs.auth.dto.RegisterDto;
import com.mkhwang.wantedcqrs.auth.exception.DuplicateUsernameException;
import com.mkhwang.wantedcqrs.user.domain.User;
import com.mkhwang.wantedcqrs.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRegisterService {
  private final UserRepository userRepository;

  @Transactional
  public void register(RegisterDto registerDto) {
    userRepository.findByEmail(registerDto.getEmail())
            .ifPresent(user -> {
              throw new DuplicateUsernameException();
            });

    userRepository.save(User.of(registerDto.getEmail(),
            registerDto.getPassword(),
            registerDto.getName()));
  }
}
