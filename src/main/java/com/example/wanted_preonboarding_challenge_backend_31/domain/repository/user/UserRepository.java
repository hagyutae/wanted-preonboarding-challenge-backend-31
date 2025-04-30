package com.example.wanted_preonboarding_challenge_backend_31.domain.repository.user;

import com.example.wanted_preonboarding_challenge_backend_31.domain.model.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from users order by random() limit 1", nativeQuery = true)
    Optional<User> findRandomOne();
}
