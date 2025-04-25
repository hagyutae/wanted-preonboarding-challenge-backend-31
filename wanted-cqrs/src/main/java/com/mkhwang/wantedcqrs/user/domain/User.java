package com.mkhwang.wantedcqrs.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;

@Getter
@Entity(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  @Column(name = "avatar_url")
  private String avatarUrl;
  @CreatedDate
  private Instant createdAt;
  @Transient
  private String password;

  public UserDetails toLoginUser() {
    return LoginUser.of(this);
  }
}
