package com.mkhwang.wantedcqrs.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;

@Getter
@Entity(name = "users")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

  public static User of(String name, String email, String password) {
    User user = new User();
    user.name = name;
    user.email = email;
    user.password = password;
    return user;
  }
}
