package com.shopping.mall.user;

import com.shopping.mall.user.entity.User;
import com.shopping.mall.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDataInitializer {

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
            userRepository.save(new User("홍길동", "https://example.com/avatars/user1.jpg"));
            userRepository.save(new User("김영희", "https://example.com/avatars/user2.jpg"));
            userRepository.save(new User("박철수", "https://example.com/avatars/user3.jpg"));
        }
    }
}
