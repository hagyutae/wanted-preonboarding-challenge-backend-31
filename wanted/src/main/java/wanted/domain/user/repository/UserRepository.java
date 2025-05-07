package wanted.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
