package com.psh10066.commerce.infrastructure.dao.user;

import com.psh10066.commerce.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
