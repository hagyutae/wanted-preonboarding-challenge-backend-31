package wanted.shop.user.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import wanted.shop.review.domain.entity.Review;
import wanted.shop.review.domain.entity.ReviewId;
import wanted.shop.user.domain.User;
import wanted.shop.user.domain.UserId;

public interface UserDataRepository extends JpaRepository<User, Long> {
}
