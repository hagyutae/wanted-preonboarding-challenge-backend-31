package com.shopping.mall.user.repository;

import com.shopping.mall.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}