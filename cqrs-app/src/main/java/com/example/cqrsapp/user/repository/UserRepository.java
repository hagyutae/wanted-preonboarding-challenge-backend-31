package com.example.cqrsapp.user.repository;

import com.example.cqrsapp.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
