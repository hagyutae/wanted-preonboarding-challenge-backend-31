package com.mkhwang.wantedcqrs.user.application;

import com.mkhwang.wantedcqrs.auth.dto.RegisterDto;
import com.mkhwang.wantedcqrs.auth.exception.DuplicateUsernameException;
import com.mkhwang.wantedcqrs.user.domain.User;
import com.mkhwang.wantedcqrs.user.infra.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRegisterServiceTest {
  @Mock
  UserRepository userRepository;
  @InjectMocks
  UserRegisterService userRegisterService;

  @DisplayName("회원가입 테스트")
  @Test
  void test_register() {
    // given
    RegisterDto dto = new RegisterDto("홍길동", "test@example.com", "password");
    when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());

    // when
    userRegisterService.register(dto);

    // then
    verify(userRepository).save(argThat(user ->
                    user.getName().equals(dto.getName()) &&
                            user.getEmail().equals(dto.getEmail())
    ));
  }

  @DisplayName("회원가입 실패 테스트")
  @Test
  void test_register_fail() {
    // given
    RegisterDto dto = new RegisterDto("홍길동", "test@example.com", "password");
    User existingUser = User.of("홍길동", "test@example.com", "password");
    when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(existingUser));

    // when & then
    assertThrows(DuplicateUsernameException.class, () -> {
      userRegisterService.register(dto);
    });

    verify(userRepository, never()).save(any(User.class));
  }
}