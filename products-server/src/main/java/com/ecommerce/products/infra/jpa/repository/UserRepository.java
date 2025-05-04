package com.ecommerce.products.infra.jpa.repository;

import com.ecommerce.products.infra.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}