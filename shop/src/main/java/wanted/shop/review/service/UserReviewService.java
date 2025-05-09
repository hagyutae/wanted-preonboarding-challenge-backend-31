package wanted.shop.review.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import wanted.shop.user.domain.User;
import wanted.shop.user.domain.UserId;
import wanted.shop.user.respository.UserRepository;

@Component
@AllArgsConstructor
public class UserReviewService {

    private final UserRepository userRepository;

    public User findOrThrow(UserId userId) {
        return userRepository.findById(userId);
    }
}
