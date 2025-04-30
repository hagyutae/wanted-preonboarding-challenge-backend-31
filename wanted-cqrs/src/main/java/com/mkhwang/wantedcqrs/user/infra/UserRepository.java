package com.mkhwang.wantedcqrs.user.infra;

import com.mkhwang.wantedcqrs.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByName(String username);

  Optional<User> findByEmail(String email);
}
