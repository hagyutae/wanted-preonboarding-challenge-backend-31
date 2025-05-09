package wanted.shop.user.respository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.domain.entity.ReviewId;
import wanted.shop.review.respository.ReviewDataRepository;
import wanted.shop.user.domain.User;
import wanted.shop.user.domain.UserId;

@Repository
@AllArgsConstructor
public class UserRepository {

    private final UserDataRepository dataRepository;

    public User findById(UserId userId) {
        return dataRepository.findById(userId.getValue())
                .orElseThrow(() -> new RuntimeException("없는 user입니다"));
    }
}
