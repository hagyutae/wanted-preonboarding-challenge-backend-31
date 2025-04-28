package com.mkhwang.wantedcqrs.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Getter
public class LoginUser implements UserDetails {
  private Long id;
  private String name;
  private String email;
  private String avatarUrl;
  private Instant createdAt;

  private LoginUser() { }

  public static LoginUser of(User user) {
    LoginUser loginUser = new LoginUser();
    loginUser.id = user.getId();
    loginUser.name = user.getName();
    loginUser.email = user.getEmail();
    loginUser.avatarUrl = user.getAvatarUrl();
    loginUser.createdAt = user.getCreatedAt();
    return loginUser;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return "";
  }

  @JsonIgnore
  @Override
  public String getUsername() {
    return "";
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }
}
