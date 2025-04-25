package com.mkhwang.wantedcqrs.user.application;


import com.mkhwang.wantedcqrs.user.domain.User;
import com.mkhwang.wantedcqrs.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = this.userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return user.toLoginUser();
  }

  public UserDetails loadUserByUsername(Long userId) throws UsernameNotFoundException {
    User user = this.userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return user.toLoginUser();
  }
}
