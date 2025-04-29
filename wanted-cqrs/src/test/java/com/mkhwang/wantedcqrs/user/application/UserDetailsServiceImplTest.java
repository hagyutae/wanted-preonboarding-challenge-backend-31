package com.mkhwang.wantedcqrs.user.application;

import com.mkhwang.wantedcqrs.user.infra.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserDetailsServiceImpl userDetailsService;

  @DisplayName("loadUserByUsername username 테스트")
  @Test
  void test_loadUserByUsername_by_username() {
    // given
    String username = "test";
    given(userRepository.findByEmail(username)).willReturn(Optional.empty());

    // when & then
    assertThrows(UsernameNotFoundException.class, () -> {
      userDetailsService.loadUserByUsername(username);
    });
  }


  @DisplayName("loadUserByUsername userId 테스트")
  @Test
  void test_loadUserByUsername_by_userId() {
    // given
    Long userId = 1L;
    given(userRepository.findById(userId)).willReturn(Optional.empty());

    // when & then
    assertThrows(UsernameNotFoundException.class, () -> {
      userDetailsService.loadUserByUsername(userId);
    });
  }
}